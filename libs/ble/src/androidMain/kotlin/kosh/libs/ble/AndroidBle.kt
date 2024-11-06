package kosh.libs.ble

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothAdapter.EXTRA_STATE
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothDevice.PHY_LE_1M_MASK
import android.bluetooth.BluetoothDevice.TRANSPORT_LE
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.bluetooth.le.ScanSettings.MATCH_MODE_STICKY
import android.bluetooth.le.ScanSettings.SCAN_MODE_BALANCED
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.BLUETOOTH_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Handler
import android.os.HandlerThread
import android.os.ParcelUuid
import arrow.core.continuations.AtomicRef
import arrow.core.continuations.update
import arrow.fx.coroutines.Resource
import arrow.fx.coroutines.resource
import co.touchlab.kermit.Logger
import kosh.libs.transport.Device
import kosh.libs.transport.Transport
import kosh.libs.transport.TransportException.PermissionNotGrantedException
import kosh.libs.transport.TransportException.TransportDisabledException
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.plus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.android.asCoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.seconds
import kotlin.uuid.Uuid
import kotlin.uuid.toJavaUuid
import kotlin.uuid.toKotlinUuid

class AndroidBle(
    private val context: Context,
) : Ble {
    private val logger = Logger.withTag("[K]AndroidBle")

    private val bluetoothManager = context.getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
    private val handlerThread = HandlerThread("BLE").also { it.start() }
    private val handler = Handler(handlerThread.looper)
    private val dispatcher = handler.asCoroutineDispatcher()
    private val coroutineScope = CoroutineScope(dispatcher + SupervisorJob())

    private val discoveredDevices = MutableStateFlow(persistentSetOf<BleDevice>())
    private val enabled = MutableStateFlow(
        bluetoothManager.adapter.state == BluetoothAdapter.STATE_ON
    )
    private val configs = AtomicRef(persistentListOf<BleConfig>())

    private val scanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            addDevice(
                device = result.device,
                services = result.scanRecord?.serviceUuids?.map { it.uuid.toKotlinUuid() }
                    ?: listOf()
            )
        }

        override fun onBatchScanResults(results: List<ScanResult>) {
            results.forEach { result ->
                addDevice(
                    device = result.device,
                    services = result.scanRecord?.serviceUuids?.map { it.uuid.toKotlinUuid() }
                        ?: listOf()
                )
            }
        }

        @SuppressLint("MissingPermission")
        private fun addDevice(device: BluetoothDevice, services: List<Uuid>) {
            discoveredDevices.update {
                it + BleDevice(Device(device.address, device.name), services)
            }
        }

        override fun onScanFailed(errorCode: Int) {
            logger.e { "onScanFailed, code=$errorCode" }
        }
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                BluetoothAdapter.ACTION_STATE_CHANGED -> {
                    val state = intent.getIntExtra(EXTRA_STATE, BluetoothAdapter.ERROR)
                    logger.v { "bluetooth state changed: $state" }
                    enabled.value = state == BluetoothAdapter.STATE_ON
                }
            }
        }
    }

    init {
        coroutineScope.launch {
            context.registerReceiver(receiver, IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED))

            discoveredDevices.subscriptionCount
                .map { it > 0 }
                .distinctUntilChanged()
                .combine(enabled) { scan, enabled -> scan to enabled }
                .collectLatest { (scan, enabled) ->
                    logger.v { "scan=$scan, enabled=$enabled" }
                    if (enabled) {
                        if (scan) {
                            startScan()
                        } else {
                            stopScan()
                        }
                    } else {
                        discoveredDevices.update { persistentSetOf() }
                    }
                }
        }
    }

    override fun register(config: BleConfig) {
        configs.update { it + config }
    }

    @SuppressLint("MissingPermission")
    private fun startScan() {
        logger.v { "startScan()" }

        if (hasBlePermission()) {
            val scanSettings = ScanSettings.Builder()
                .setScanMode(SCAN_MODE_BALANCED)
                .setMatchMode(MATCH_MODE_STICKY)
                .build()

            val filters = configs.get().flatMap { it.serviceUuids }
                .map { ScanFilter.Builder().setServiceUuid(ParcelUuid(it.toJavaUuid())).build() }

            bluetoothManager.adapter.bluetoothLeScanner?.startScan(
                filters,
                scanSettings,
                scanCallback
            )
        }
    }

    @SuppressLint("MissingPermission")
    private suspend fun stopScan() {
        logger.v { "stopScan()" }
        bluetoothManager.adapter.bluetoothLeScanner?.stopScan(scanCallback)
        delay(10.seconds)
        discoveredDevices.update { persistentSetOf() }
    }

    @SuppressLint("MissingPermission")
    override fun devices(config: BleConfig): Flow<List<Device>> = discoveredDevices
        .map { devices -> devices.filter { device -> device.services.any { it in config.serviceUuids } } }
        .map { devices -> devices.map { it.device } }
        .map { peripherals -> peripherals.sortedBy { it.id } }

    @SuppressLint("MissingPermission")
    override suspend fun open(
        id: String,
        config: BleConfig,
    ): Resource<Transport.Connection> = withContext(dispatcher) {
        if (!enabled.value) throw TransportDisabledException()
        if (!hasBlePermission()) throw PermissionNotGrantedException("Bluetooth Permission Not Granted")
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            if (!hasLocationPermission()) throw PermissionNotGrantedException("Location Permission Not Granted")
        }

        val device = bluetoothManager.adapter.getRemoteDevice(id)

        resource {
            val connection = install(
                { AndroidBleConnection(config, dispatcher) },
                { it, _ -> it.close() }
            )

            install(
                { device.connect(connection) },
                { it, _ -> it.close() }
            )

            connection.also {
                it.initialize()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun BluetoothDevice.connect(
        connection: AndroidBleConnection,
    ) = connectGatt(context, false, connection, TRANSPORT_LE, PHY_LE_1M_MASK, handler)

    private fun hasLocationPermission(): Boolean =
        context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

    private fun hasBlePermission(): Boolean = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        context.checkSelfPermission(Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED
                && context.checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED
    } else {
        true
    }
}

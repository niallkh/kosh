package kosh.libs.ble

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothAdapter.EXTRA_STATE
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothDevice.TRANSPORT_LE
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.bluetooth.le.ScanSettings.CALLBACK_TYPE_ALL_MATCHES
import android.bluetooth.le.ScanSettings.CALLBACK_TYPE_FIRST_MATCH
import android.bluetooth.le.ScanSettings.CALLBACK_TYPE_MATCH_LOST
import android.bluetooth.le.ScanSettings.SCAN_MODE_LOW_LATENCY
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
import kotlinx.collections.immutable.minus
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.plus
import kotlinx.coroutines.android.asCoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlin.uuid.toJavaUuid

class AndroidBle(
    private val context: Context,
) : Ble {

    private val bluetoothManager by lazy { context.getSystemService(BLUETOOTH_SERVICE) as BluetoothManager }

    private val handlerThread by lazy { HandlerThread("BLE").also { it.start() } }
    private val handler by lazy { Handler(handlerThread.looper) }
    private val dispatcher by lazy { handler.asCoroutineDispatcher() }

    @SuppressLint("MissingPermission")
    override fun devices(config: BleConfig): Flow<List<Device>> {
        return listDevices(context, config)
            .map { list ->
                list.map { Device(it.address, it.name) }
                    .sortedBy { it.id }
            }
    }

    @SuppressLint("MissingPermission")
    override suspend fun open(
        id: String,
        config: BleConfig,
    ): Resource<Transport.Connection> = withContext(dispatcher) {
        require(bluetoothManager.adapter.isEnabled) { "Bluetooth is disabled" }
        require(hasBlePermission()) { "Bluetooth Permission Not Granted" }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            require(hasLocationPermission()) { "Location Permission Not Granted" }
        }

        val device = bluetoothManager.adapter.getRemoteDevice(id)

        resource {

            val connection = install(
                { AndroidBleConnection(config, dispatcher) },
                { it, _ -> it.close() }
            )

            install(
                {
                    device.connectGatt(
                        context,
                        false,
                        connection,
                        TRANSPORT_LE,
                        BluetoothDevice.PHY_LE_1M_MASK,
                        handler,
                    )
                },
                { it, _ -> it.close() }
            )

            connection.also {
                it.initialize()
            }
        }
    }

    private fun hasLocationPermission(): Boolean =
        context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

    private fun hasBlePermission(): Boolean = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        context.checkSelfPermission(Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED
                && context.checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED
    } else {
        true
    }
}

@SuppressLint("MissingPermission")
private fun listDevices(
    context: Context,
    config: BleConfig,
) = channelFlow {
    val logger = Logger.withTag("[K]BluetoothScan")

    val bluetoothManager = context.getSystemService(BLUETOOTH_SERVICE) as BluetoothManager

    val devices = AtomicRef(persistentSetOf<BluetoothDevice>())

    fun addDevice(device: BluetoothDevice) {
        trySend(devices.updateAndGet { it + device })
    }

    fun removeDevice(device: BluetoothDevice) {
        trySend(devices.updateAndGet { it - device })
    }

    val scanCallback: ScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            when (callbackType) {
                CALLBACK_TYPE_ALL_MATCHES,
                CALLBACK_TYPE_FIRST_MATCH,
                    -> addDevice(result.device)

                CALLBACK_TYPE_MATCH_LOST -> removeDevice(result.device)
            }
        }

        override fun onBatchScanResults(results: List<ScanResult>) {
            results.forEach { addDevice(it.device) }
        }

        override fun onScanFailed(errorCode: Int) {
            logger.e { "onScanFailed, code=$errorCode" }
        }
    }

    fun start() {
        logger.v { "start" }
        val scanSettings = ScanSettings.Builder()
            .setScanMode(SCAN_MODE_LOW_LATENCY)
            .build()

        val filters = config.serviceUuid
            .map { ScanFilter.Builder().setServiceUuid(ParcelUuid(it.toJavaUuid())).build() }

        bluetoothManager.adapter.bluetoothLeScanner?.startScan(filters, scanSettings, scanCallback)
    }

    fun stop() {
        logger.v { "stop" }
        bluetoothManager.adapter.bluetoothLeScanner?.stopScan(scanCallback)
    }

    val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                BluetoothAdapter.ACTION_STATE_CHANGED -> {
                    val state = intent.getIntExtra(EXTRA_STATE, BluetoothAdapter.ERROR)
                    logger.v { "bluetooth state changed: $state" }
                    devices.update { persistentSetOf() }

                    when (state) {
                        BluetoothAdapter.STATE_ON -> start()
                        else -> stop()
                    }
                }
            }
        }
    }

    fun register() {
        context.registerReceiver(receiver, IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED))
    }

    fun unregister() = context.unregisterReceiver(receiver)

    register()
    start()

    awaitClose {
        unregister()
        stop()
    }
}
    .conflate()

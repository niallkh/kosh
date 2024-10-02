package kosh.libs.ble

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothDevice.TRANSPORT_AUTO
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.content.Context.BLUETOOTH_SERVICE
import android.content.pm.PackageManager
import android.os.Build
import android.os.Handler
import android.os.HandlerThread
import android.os.ParcelUuid
import arrow.core.continuations.AtomicRef
import arrow.fx.coroutines.Resource
import arrow.fx.coroutines.resource
import co.touchlab.kermit.Logger
import kosh.libs.transport.Device
import kosh.libs.transport.Transport
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.plus
import kotlinx.coroutines.android.asCoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOn
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

    private val scanner: BluetoothLeScanner?
        get() = bluetoothManager.adapter.bluetoothLeScanner

    @SuppressLint("MissingPermission")
    override fun devices(config: BleConfig): Flow<List<Device>> =
        (scanner?.let { listDevices(it, config) } ?: emptyFlow())
            .map { list -> list.map { Device(it.address, it.name) } }
            .distinctUntilChanged()
            .flowOn(dispatcher)

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
                        TRANSPORT_AUTO,
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
    scanner: BluetoothLeScanner,
    config: BleConfig,
) = channelFlow {
    val logger = Logger.withTag("[K]BluetoothScan")

    val devices = AtomicRef(persistentSetOf<BluetoothDevice>())

    val scanCallback: ScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            super.onScanResult(callbackType, result)
            result?.device?.let { device ->
                addDevice(device)
            }
        }

        override fun onBatchScanResults(results: List<ScanResult>) {
            super.onBatchScanResults(results)
            results.forEach { addDevice(it.device) }
        }

        private fun addDevice(device: BluetoothDevice) {
            trySend(devices.updateAndGet { it + device })
        }

        override fun onScanFailed(errorCode: Int) {
            super.onScanFailed(errorCode)
            logger.e { "onScanFailed, code=$errorCode" }
        }
    }

    fun start() {
        logger.v { "start" }
        val scanSettings = ScanSettings.Builder().build()

        val filters = config.serviceUuid
            .map { ScanFilter.Builder().setServiceUuid(ParcelUuid(it.toJavaUuid())).build() }

        scanner.startScan(filters, scanSettings, scanCallback)
    }

    fun stop() {
        logger.v { "stop" }
        scanner.stopScan(scanCallback)
    }

    start()
    awaitClose { stop() }
}

package kosh.libs.ble

import arrow.fx.coroutines.ExitCase
import arrow.fx.coroutines.Resource
import arrow.fx.coroutines.resource
import co.touchlab.kermit.Logger
import kosh.libs.transport.Device
import kosh.libs.transport.Transport
import kosh.libs.transport.TransportException.ConnectionFailedException
import kosh.libs.transport.TransportException.TransportDisabledException
import kotlinx.atomicfu.atomic
import kotlinx.atomicfu.update
import kotlinx.collections.immutable.minus
import kotlinx.collections.immutable.persistentHashSetOf
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.plus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import platform.CoreBluetooth.CBAdvertisementDataServiceUUIDsKey
import platform.CoreBluetooth.CBCentralManager
import platform.CoreBluetooth.CBCentralManagerDelegateProtocol
import platform.CoreBluetooth.CBCentralManagerScanOptionAllowDuplicatesKey
import platform.CoreBluetooth.CBManagerStatePoweredOn
import platform.CoreBluetooth.CBPeripheral
import platform.CoreBluetooth.CBUUID
import platform.Foundation.NSError
import platform.Foundation.NSNumber
import platform.Foundation.NSUUID
import platform.darwin.DISPATCH_QUEUE_CONCURRENT
import platform.darwin.NSObject
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_current_queue
import platform.darwin.dispatch_queue_create
import platform.darwin.dispatch_queue_t
import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration.Companion.seconds
import kotlin.uuid.Uuid

class IosBle : Ble {

    private val logger = Logger.withTag("[K]IosBle")

    private val dispatchQueue = dispatch_queue_create("BLE", DISPATCH_QUEUE_CONCURRENT)
    private val dispatcher = dispatchQueue.asCoroutineDispatcher()
    private val coroutineScope = CoroutineScope(dispatcher + SupervisorJob())

    private val enabled = MutableStateFlow(false)
    private val discoveredDevices = MutableStateFlow(persistentSetOf<BleDevice>())
    private val connected = MutableStateFlow(persistentHashSetOf<String>())
    private val configs = atomic(persistentListOf<BleConfig>())

    private val callback = object : NSObject(), CBCentralManagerDelegateProtocol {
        override fun centralManagerDidUpdateState(central: CBCentralManager) {
            logger.v { "centralManagerDidUpdateState: ${central.state}" }
            enabled.value = central.state == CBManagerStatePoweredOn
        }

        override fun centralManager(
            central: CBCentralManager,
            didDiscoverPeripheral: CBPeripheral,
            advertisementData: Map<Any?, *>,
            RSSI: NSNumber,
        ) {
            val services = advertisementData[CBAdvertisementDataServiceUUIDsKey] as? List<*>
                ?: listOf<Any>()

            discoveredDevices.update { devices ->
                val newDevice = BleDevice(
                    device = Device(
                        id = didDiscoverPeripheral.identifier.UUIDString,
                        name = didDiscoverPeripheral.name,
                    ),
                    services = services.map { Uuid.parse((it as CBUUID).UUIDString) },
                )
                devices + newDevice
            }
        }

        override fun centralManager(
            central: CBCentralManager,
            didConnectPeripheral: CBPeripheral,
        ) {
            logger.v { "didConnectPeripheral: ${didConnectPeripheral.identifier.UUIDString}" }
            connected.update { it + didConnectPeripheral.identifier.UUIDString }
        }

        override fun centralManager(
            central: CBCentralManager,
            didDisconnectPeripheral: CBPeripheral,
            error: NSError?,
        ) {
            logger.v { "didDisconnectPeripheral: ${didDisconnectPeripheral.identifier.UUIDString}" }
            connected.update { it - didDisconnectPeripheral.identifier.UUIDString }
        }
    }

    private val bleManager = CBCentralManager(callback, dispatchQueue)

    init {
        coroutineScope.launch {
            discoveredDevices.subscriptionCount
                .map { it > 0 }
                .distinctUntilChanged()
                .combine(enabled) { scan, enabled -> scan to enabled }
                .collectLatest { (scan, enabled) ->
                    logger.v { "scan=$scan, enabled=$enabled" }
                    if (enabled) {
                        if (scan) startScan()
                        else stopScan()
                    } else {
                        discoveredDevices.update { persistentSetOf() }
                    }
                }
        }
    }

    override fun register(config: BleConfig) {
        configs.update { it + config }
    }

    override fun devices(config: BleConfig): Flow<List<Device>> = discoveredDevices
        .map { devices -> devices.filter { device -> device.services.any { it in config.serviceUuids } } }
        .map { devices -> devices.map { it.device } }
        .map { peripherals -> peripherals.sortedBy { it.id } }

    private fun startScan() {
        logger.v { "startScan()" }
        bleManager.scanForPeripheralsWithServices(
            serviceUUIDs = configs.value.flatMap { it.serviceUuids }.map { cbUuid(it) },
            options = mapOf(CBCentralManagerScanOptionAllowDuplicatesKey to false),
        )
    }

    private suspend fun stopScan() {
        logger.v { "stopScan()" }
        bleManager.stopScan()
        delay(10.seconds)
        discoveredDevices.update { persistentSetOf() }
    }

    override suspend fun open(
        id: String,
        config: BleConfig,
    ): Resource<Transport.Connection> = withContext(dispatcher) {
        if (!enabled.value) throw TransportDisabledException()
        val peripheral = bleManager
            .retrievePeripheralsWithIdentifiers(listOf(nsUuid(Uuid.parse(id))))
            .firstOrNull() as? CBPeripheral
            ?: throw ConnectionFailedException("Device not found")

        resource {
            install(
                { bleManager.connectPeripheral(peripheral, null) },
                { _, exitCase ->
                    when (exitCase) {
                        is ExitCase.Cancelled,
                        is ExitCase.Failure,
                            -> delay(1.seconds) // device hand if disconnected abruptly
                        ExitCase.Completed -> Unit
                    }
                    logger.v { "cancelPeripheralConnection, $exitCase" }
                    bleManager.cancelPeripheralConnection(peripheral)
                }
            )

            withTimeoutOrNull(10.seconds) {
                connected.first { id in it }
            } ?: throw ConnectionFailedException("Device not connected")

            install(
                { IosBleConnection(config, peripheral, dispatcher) },
                { it, _ -> it.close() },
            ).also {
                it.initialize()
            }
        }
    }
}

internal fun nsUuid(it: Uuid): NSUUID = NSUUID(it.toString())
internal fun cbUuid(it: Uuid): CBUUID = CBUUID.UUIDWithString(it.toString())

internal fun dispatch_queue_t.asCoroutineDispatcher(): CoroutineDispatcher =
    object : CoroutineDispatcher() {

        override fun isDispatchNeeded(context: CoroutineContext): Boolean {
            return dispatch_get_current_queue() != this@asCoroutineDispatcher
        }

        override fun dispatch(context: CoroutineContext, block: Runnable) {
            dispatch_async(this@asCoroutineDispatcher) {
                block.run()
            }
        }
    }

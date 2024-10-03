package kosh.libs.ble

import arrow.fx.coroutines.Resource
import arrow.fx.coroutines.resource
import co.touchlab.kermit.Logger
import kosh.libs.transport.Device
import kosh.libs.transport.Transport
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.withContext
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
import kotlin.uuid.Uuid

class IosBle : Ble {

    private val logger = Logger.withTag("[K]IosBle")

    private val dispatchQueue by lazy { dispatch_queue_create("BLE", DISPATCH_QUEUE_CONCURRENT) }
    private val dispatcher by lazy { dispatchQueue.asCoroutineDispatcher() }

    private val enabled = MutableStateFlow(false)
    private val connected = MutableStateFlow(false)
    private val discoveredDevices = Channel<CBPeripheral>()
    private val bleManager by lazy { CBCentralManager(callback, dispatchQueue) }

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
            discoveredDevices.trySend(didDiscoverPeripheral)
        }

        override fun centralManager(
            central: CBCentralManager,
            didConnectPeripheral: CBPeripheral,
        ) {
            logger.v { "didConnectPeripheral: ${didConnectPeripheral.identifier.UUIDString}" }
            connected.value = true
        }

        override fun centralManager(
            central: CBCentralManager,
            didDisconnectPeripheral: CBPeripheral,
            error: NSError?,
        ) {
            logger.v { "didDisconnectPeripheral: ${didDisconnectPeripheral.identifier.UUIDString}" }
            connected.value = false
        }
    }

    override fun devices(config: BleConfig): Flow<List<Device>> = connected.flatMapLatest {
        if (!it) listDevices(config) else emptyFlow()
    }
        .flowOn(dispatcher)

    private fun listDevices(config: BleConfig) = discoveredDevices.receiveAsFlow()
        .distinctUntilChanged()
        .onStart {
            enabled.first { it }
            bleManager.scanForPeripheralsWithServices(
                config.serviceUuid.map { cbUuid(it) },
                mapOf(CBCentralManagerScanOptionAllowDuplicatesKey to true),
            )
        }
        .onCompletion { bleManager.stopScan() }
        .map {
            listOf(Device(it.identifier.UUIDString, it.name))
                .sortedBy { it.id }
        }

    override suspend fun open(
        id: String,
        config: BleConfig,
    ): Resource<Transport.Connection> = withContext(dispatcher) {
        val peripheral = bleManager
            .retrievePeripheralsWithIdentifiers(listOf(nsUuid(Uuid.parse(id))))
            .first() as CBPeripheral

        resource {
            install(
                { bleManager.connectPeripheral(peripheral, null) },
                { _, _ -> bleManager.cancelPeripheralConnection(peripheral) }
            )

            connected.first { it }

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

internal fun dispatch_queue_t.asCoroutineDispatcher(): CoroutineDispatcher {
    return object : CoroutineDispatcher() {

        override fun isDispatchNeeded(context: CoroutineContext): Boolean {
            return dispatch_get_current_queue() != this@asCoroutineDispatcher
        }

        override fun dispatch(context: CoroutineContext, block: Runnable) {
            dispatch_async(this@asCoroutineDispatcher) {
                block.run()
            }
        }
    }
}

package kosh.libs.usb

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.UsbConstants
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbDeviceConnection
import android.hardware.usb.UsbEndpoint
import android.hardware.usb.UsbInterface
import android.hardware.usb.UsbManager
import android.os.Build
import arrow.fx.coroutines.Resource
import arrow.fx.coroutines.resource
import arrow.resilience.Schedule
import arrow.resilience.retry
import arrow.resilience.retryOrElse
import co.touchlab.kermit.Logger
import kosh.libs.transport.Device
import kosh.libs.transport.Transport
import kosh.libs.transport.TransportException.ConnectionFailedException
import kosh.libs.transport.TransportException.DeviceDisconnectedException
import kosh.libs.transport.TransportException.PermissionNotGrantedException
import kotlinx.atomicfu.atomic
import kotlinx.atomicfu.update
import kotlinx.collections.immutable.minus
import kotlinx.collections.immutable.persistentHashSetOf
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.plus
import kotlinx.collections.immutable.toPersistentHashSet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

internal const val ACTION_USB_PERMISSION = "Kosh.USB_PERMISSION"

private val retrySchedule = Schedule.linear<Throwable>(300.milliseconds) and Schedule.recurs(3)

class AndroidUsb(
    private val context: Context,
) : Usb {
    private val logger = Logger.withTag("[K]AndroidUsb")

    private val dispatcher = Dispatchers.IO.limitedParallelism(1, "USB")
    private val coroutineScope = CoroutineScope(dispatcher + SupervisorJob())

    private val discoveredDevices = MutableStateFlow(persistentHashSetOf<WrappedDevice>())
    private val configs = atomic(persistentListOf<UsbConfig>())

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            Logger.v { "BroadcastReceiver1#onReceive(action = ${intent.action})" }
            val device = intent.usbDevice?.mapDevice()

            if (device != null) {
                when (intent.action) {
                    UsbManager.ACTION_USB_DEVICE_ATTACHED -> discoveredDevices.update { it + device }
                    UsbManager.ACTION_USB_DEVICE_DETACHED -> discoveredDevices.update { it - device }
                }
            }
        }
    }

    init {
        coroutineScope.launch {
            context.registerReceiver(receiver, IntentFilter(UsbManager.ACTION_USB_DEVICE_ATTACHED))
            context.registerReceiver(receiver, IntentFilter(UsbManager.ACTION_USB_DEVICE_DETACHED))

            discoveredDevices.subscriptionCount
                .map { it > 0 }
                .distinctUntilChanged()
                .collectLatest { scan ->
                    logger.v { "scan=$scan" }
                    if (scan) {
                        startScan()
                    } else {
                        stopScan()
                    }
                }
        }
    }

    override fun register(config: UsbConfig) {
        configs.update { it + config }
    }

    private fun startScan() {
        logger.v { "startScan()" }
        val initialDevices = context.usbManager.deviceList.values.toList()
            .map { it.mapDevice() }
            .toPersistentHashSet()

        discoveredDevices.update { initialDevices }

        context.registerReceiver(receiver, IntentFilter(UsbManager.ACTION_USB_DEVICE_ATTACHED))
        context.registerReceiver(receiver, IntentFilter(UsbManager.ACTION_USB_DEVICE_DETACHED))
    }

    private suspend fun stopScan() {
        logger.v { "stopScan()" }
        context.unregisterReceiver(receiver)
        delay(10.seconds)
        discoveredDevices.update { persistentHashSetOf() }
    }

    override fun devices(config: UsbConfig): Flow<List<Device>> = discoveredDevices
        .map { devices ->
            devices.filter {
                it.vendorId in config.vendorIds &&
                        (it.productId in config.productIds || it.productId shr 8 in config.productIds)
            }
        }
        .map { devices -> devices.map { it.device } }
        .map { peripherals -> peripherals.sortedBy { it.id } }

    override suspend fun open(
        id: String,
        config: UsbConfig,
    ): Resource<Transport.Connection> = withContext(dispatcher) {
        logger.v { "open(id = ${id})" }
        val usbDevice = context.usbManager.requireDevice { it.deviceId.toString() == id }

        if (context.usbManager.hasPermission(usbDevice).not()) {
            logger.v { "requestPermission(id = ${id})" }
            val granted = requestPermission(context, usbDevice)
            logger.v { "permission granted=$granted" }
            if (granted.not())
                throw PermissionNotGrantedException("Usb permission not granted")
        }

        resource {
            val connection = install(
                acquire = {
                    context.usbManager.openDevice(usbDevice)
                        ?: throw ConnectionFailedException("Couldn't open usb connection")
                },
                release = { it, _ -> it.close() }
            )

            val usbInterface = usbInterface(usbDevice, config)

            install(
                acquire = {
                    if (connection.claimInterface(usbInterface).not())
                        throw ConnectionFailedException("Usb interface not claimed")
                },
                release = { _, _ -> connection.releaseInterface(usbInterface) }
            )

            val writeEndpoint = usbEndpoint(
                config, usbInterface, true,
            )
            val readEndpoint = usbEndpoint(
                config, usbInterface, false,
            )

            install(
                acquire = {
                    AndroidUsbConnection(
                        connection = connection,
                        writeEndpoint = writeEndpoint,
                        readEndpoint = readEndpoint,
                        packetSize = config.packetSize,
                    )
                },
                release = { it, _ -> it.close() }
            )
        }
    }

    private suspend fun UsbManager.requireDevice(predicate: (UsbDevice) -> Boolean): UsbDevice {
        return retrySchedule.retry {
            deviceList.values.find(predicate)
                ?: throw DeviceDisconnectedException()
        }
    }

    private suspend fun UsbDeviceConnection.claimInterface(
        usbInterface: UsbInterface,
    ): Boolean = retrySchedule.retryOrElse(
        action = {
            val claimed = claimInterface(usbInterface, false)
            if (claimed.not()) {
                logger.v { "Couldn't claim usb interface" }
                error("Couldn't claim usb interface")
            }
            true
        },
        orElse = { _, _ ->
            val claimed = claimInterface(usbInterface, true)
            if (claimed.not()) {
                logger.w { "Couldn't claim usb interface forcefully" }
            }
            claimed
        }
    )
}

private suspend fun requestPermission(
    context: Context,
    device: UsbDevice,
): Boolean = suspendCancellableCoroutine { cont ->
    val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            Logger.v { "BroadcastReceiver2#onReceive(action = ${intent.action})" }
            if (intent.action == ACTION_USB_PERMISSION && device == intent.usbDevice) {
                val granted = intent.getBooleanExtra(
                    UsbManager.EXTRA_PERMISSION_GRANTED,
                    false
                )
                context.unregisterReceiver(this)
                cont.resume(granted)
            }
        }
    }

    cont.invokeOnCancellation { context.unregisterReceiver(receiver) }
    registerUsbPermissionReceiver(context, receiver)
    context.usbManager.requestPermission(device, getUsbPermissionIntent(context))
}

@SuppressLint("UnspecifiedRegisterReceiverFlag")
private fun registerUsbPermissionReceiver(
    context: Context,
    receiver: BroadcastReceiver,
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        context.registerReceiver(
            receiver,
            IntentFilter(ACTION_USB_PERMISSION),
            Context.RECEIVER_EXPORTED,
        )
    } else {
        context.registerReceiver(receiver, IntentFilter(ACTION_USB_PERMISSION))
    }
}

private fun getUsbPermissionIntent(context: Context): PendingIntent {
    val intent = Intent(ACTION_USB_PERMISSION).apply {
        setPackage(context.packageName)
    }

    return PendingIntent.getBroadcast(
        context,
        0,
        intent,
        PendingIntent.FLAG_MUTABLE
    )
}

private fun usbInterface(
    usbDevice: UsbDevice,
    config: UsbConfig,
): UsbInterface = usbDevice.getInterface(config.usbInterfaceNumber)

private fun usbEndpoint(
    config: UsbConfig,
    usbInterface: UsbInterface,
    write: Boolean,
): UsbEndpoint {
    return (0..<usbInterface.endpointCount).asSequence()
        .map { usbInterface.getEndpoint(it) }
        .filter {
            if (write) it.direction == UsbConstants.USB_DIR_OUT
            else it.direction == UsbConstants.USB_DIR_IN
        }
//        .filter { it.type == UsbConstants.USB_ENDPOINT_XFER_INT }
        .filter { it.maxPacketSize == config.packetSize }
        .firstOrNull()
        ?: throw ConnectionFailedException("Usb endpoint not found, write=$write")
}

private val Context.usbManager: UsbManager
    inline get() = getSystemService(Context.USB_SERVICE) as UsbManager

internal val Intent.usbDevice: UsbDevice?
    inline get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelableExtra(UsbManager.EXTRA_DEVICE, UsbDevice::class.java)
    } else {
        getParcelableExtra(UsbManager.EXTRA_DEVICE)
    }

internal fun UsbDevice.mapDevice() = WrappedDevice(
    device = Device(
        id = deviceId.toString(),
        name = productName,
    ),
    vendorId = vendorId,
    productId = productId,
)

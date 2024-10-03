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
import arrow.core.continuations.AtomicRef
import arrow.fx.coroutines.Resource
import arrow.fx.coroutines.resource
import arrow.resilience.Schedule
import arrow.resilience.retry
import arrow.resilience.retryOrElse
import co.touchlab.kermit.Logger
import kosh.libs.transport.Device
import kosh.libs.transport.Transport
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.minus
import kotlinx.collections.immutable.plus
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.time.Duration.Companion.milliseconds

internal const val ACTION_USB_PERMISSION = "Kosh.USB_PERMISSION"

private val retrySchedule = Schedule.linear<Throwable>(300.milliseconds) and Schedule.recurs(3)

class AndroidUsb(
    private val context: Context,
) : Usb {
    private val logger = Logger.withTag("[K]Usb")

    override fun devices(config: UsbConfig): Flow<List<Device>> = listDevices(context)
        .map { devices ->
            devices
                .filter {
                    it.vendorId in config.vendorIds &&
                            (it.productId in config.productIds || it.productId shr 8 in config.productIds)
                }
                .map { it.mapDevice() }
                .sortedBy { it.id }
        }

    override suspend fun open(
        id: String,
        config: UsbConfig,
    ): Resource<Transport.Connection> = withContext(Dispatchers.IO) {
        logger.d { "open(id = ${id})" }
        val usbDevice = context.usbManager.requireDevice { it.deviceId.toString() == id }

        if (context.usbManager.hasPermission(usbDevice).not()) {
            logger.i { "requestPermission(id = ${id})" }
            val granted = requestPermission(context, usbDevice)
            logger.i { "permission granted=$granted" }
            if (granted.not()) {
                throw PermissionNotGrantedException()
            }
        }

        resource {
            val connection = install(
                acquire = {
                    context.usbManager.openDevice(usbDevice)
                        ?: error("Couldn't open usb connection")
                },
                release = { it, _ -> it.close() }
            )

            val usbInterface = usbInterface(usbDevice, config)

            install(
                acquire = {
                    if (connection.claimInterface(usbInterface).not()) {
                        throw UsbInterfaceNotClaimedException()
                    }
                },
                release = { _, _ ->
                    connection.releaseInterface(usbInterface)
                }
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
                ?: error("Usb device not found")
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

private fun listDevices(context: Context): Flow<List<UsbDevice>> = channelFlow {
    Logger.v { "listDevices()" }

    val initialDevices = withContext(Dispatchers.IO) {
        context.usbManager.deviceList.values.toList().toPersistentList()
    }
    val devices = AtomicRef(initialDevices)

    fun update(block: (PersistentList<UsbDevice>) -> PersistentList<UsbDevice> = { it }) {
        trySend(devices.updateAndGet(block))
    }

    val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            Logger.v { "BroadcastReceiver1#onReceive(action = ${intent.action})" }
            when (intent.action) {
                UsbManager.ACTION_USB_DEVICE_ATTACHED -> {
                    update { it + checkNotNull(intent.usbDevice) }
                }

                UsbManager.ACTION_USB_DEVICE_DETACHED -> {
                    update { it - checkNotNull(intent.usbDevice) }
                }
            }
        }
    }

    fun register() {
        context.registerReceiver(receiver, IntentFilter(UsbManager.ACTION_USB_DEVICE_ATTACHED))
        context.registerReceiver(receiver, IntentFilter(UsbManager.ACTION_USB_DEVICE_DETACHED))
    }

    fun unregister() = context.unregisterReceiver(receiver)

    update()
    register()
    awaitClose { unregister() }
}
    .conflate()

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
        .filter { it.type == UsbConstants.USB_ENDPOINT_XFER_INT }
        .filter { it.maxPacketSize == config.packetSize }
        .firstOrNull() ?: error("Usb endpoint not found")
}

private val Context.usbManager: UsbManager
    inline get() = getSystemService(Context.USB_SERVICE) as UsbManager

internal val Intent.usbDevice: UsbDevice?
    inline get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelableExtra(UsbManager.EXTRA_DEVICE, UsbDevice::class.java)
    } else {
        getParcelableExtra(UsbManager.EXTRA_DEVICE)
    }

internal fun UsbDevice.mapDevice(): Device = Device(
    id = deviceId.toString(),
    name = productName,
)

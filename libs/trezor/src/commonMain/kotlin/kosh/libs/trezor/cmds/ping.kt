package kosh.libs.trezor.cmds

import arrow.atomic.AtomicInt
import com.satoshilabs.trezor.lib.protobuf.Ping
import com.satoshilabs.trezor.lib.protobuf.Success
import kosh.libs.trezor.TrezorManager
import kotlin.math.abs
import kotlin.random.Random

private val counter = AtomicInt(abs(Random.nextInt() / 2))

suspend fun TrezorManager.Connection.ping(
    button: Boolean = true,
): Boolean {
    val message = counter.incrementAndGet().toString().padStart(10, '0')
    val response = exchange(Ping(message = message, button_protection = button))
    return response.expect<Success>().message == message
}

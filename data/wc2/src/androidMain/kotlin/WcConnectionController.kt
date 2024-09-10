package kosh.data.wc2

import arrow.atomic.AtomicInt
import co.touchlab.kermit.Logger
import com.walletconnect.android.Core
import com.walletconnect.android.CoreClient

class WcConnectionController {

    private val logger = Logger.withTag("[K]WcConnectionController")

    private val counter = AtomicInt(0)

    fun connect() {
        if (counter.getAndIncrement() == 0) {
            logger.d { "connect()" }
            CoreClient.Relay.connect { _: Core.Model.Error -> }
        }
    }

    fun disconnect() {
        if (counter.decrementAndGet() == 0) {
            logger.d { "disconnect()" }
            CoreClient.Relay.disconnect { _: Core.Model.Error -> }
        }
    }
}

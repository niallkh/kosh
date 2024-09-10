package kosh.data.wc2

import com.walletconnect.foundation.util.Logger

object WcLoggerAdapter : Logger {
    private val logger = co.touchlab.kermit.Logger.withTag("[K]Wc")

    override fun error(errorMsg: String?) {
        if (errorMsg != null) {
            logger.w { errorMsg }
        }
    }

    override fun error(throwable: Throwable?) {
        if (throwable != null) {
            logger.w(throwable) { throwable.message.orEmpty() }
        }
    }

    override fun log(logMsg: String?) {
        if (logMsg != null) {
            logger.w { logMsg }
        }
    }

    override fun log(throwable: Throwable?) {
        if (throwable != null) {
            logger.w(throwable) { throwable.message.orEmpty() }
        }
    }
}

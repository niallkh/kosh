package kosh.app

import co.touchlab.kermit.Logger

object KtorLogger : io.ktor.client.plugins.logging.Logger {

    private val logger = Logger.withTag("[K]Ktor")

    override fun log(message: String) {
        logger.v(message)
    }
}

package kosh.app

import io.ktor.client.plugins.logging.Logger
import co.touchlab.kermit.Logger.Companion as KermitLogger

object KtorLogger : Logger {

    private val logger = KermitLogger.withTag("[K]Ktor")

    override fun log(message: String) {
        logger.v(message)
    }
}

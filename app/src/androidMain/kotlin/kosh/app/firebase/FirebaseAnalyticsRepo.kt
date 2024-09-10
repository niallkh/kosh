package kosh.app.firebase

import co.touchlab.kermit.Logger
import kosh.domain.analytics.AnalyticsRepo

object FirebaseAnalyticsRepo : AnalyticsRepo {

    private val logger = Logger.withTag("[K]AnalyticsRepo")

    override fun logScreen(name: String) {
        logger.d { "logScreen(name=$name)" }
    }

    override fun logEvent(name: String, params: Map<String, String>) {
        logger.d { "logEvent(name=$name)" }
    }
}

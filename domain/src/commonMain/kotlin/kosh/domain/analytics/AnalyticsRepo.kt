package kosh.domain.analytics

interface AnalyticsRepo {

    fun logScreen(name: String)

    fun logEvent(name: String, params: Map<String, String>)
}

package kosh.domain.repositories


interface ShareRepo : Repository {

    suspend fun shareJson(
        name: String,
        json: String,
    )

    suspend fun importJson(): String
}

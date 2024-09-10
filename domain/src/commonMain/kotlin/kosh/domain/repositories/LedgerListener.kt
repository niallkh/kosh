package kosh.domain.repositories


interface LedgerListener {
    suspend fun buttonRequest(request: ButtonRequest)

    enum class ButtonRequest {
        UnlockDevice,
    }
}

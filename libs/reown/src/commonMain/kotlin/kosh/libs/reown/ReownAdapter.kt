package kosh.libs.reown

const val EIP155 = "eip155"

interface ReownAdapter {

    suspend fun initialize()

    suspend fun connect()

    suspend fun disconnect()

    fun getNewProposal(callback: (SessionProposal) -> Unit): () -> Unit

    fun getProposals(callback: (List<SessionProposal>) -> Unit): () -> Unit

    fun getNewAuthentication(callback: (AuthenticationRequest) -> Unit): () -> Unit

    fun getAuthentications(callback: (List<AuthenticationRequest>) -> Unit): () -> Unit

    fun getNewRequest(callback: (SessionRequest) -> Unit): () -> Unit

    fun getRequests(callback: (List<SessionRequest>) -> Unit): () -> Unit

    fun getSessions(callback: (List<Session>) -> Unit): () -> Unit

    @Throws(Exception::class)
    suspend fun pair(
        uri: String,
    )

    @Throws(Exception::class)
    suspend fun getProposal(
        pairingTopic: String,
    ): SessionProposal?

    @Throws(Exception::class)
    suspend fun approveProposal(
        pairingTopic: String,
        chains: List<String>,
        accounts: List<String>,
        methods: List<String>,
        events: List<String>,
    ): String?

    @Throws(Exception::class)
    suspend fun rejectProposal(
        pairingTopic: String,
        reason: String,
    ): String?

    @Throws(Exception::class)
    suspend fun getAuthentication(
        id: Long,
    ): AuthenticationRequest?

    @Throws(Exception::class)
    suspend fun approveAuthentication(
        id: Long,
        issuer: String,
        supportedChains: List<String>,
        supportedMethods: List<String>,
        signature: String,
    ): String?

    @Throws(Exception::class)
    suspend fun getAuthenticationMessage(
        id: Long,
        issuer: String,
        supportedChains: List<String>,
        supportedMethods: List<String>,
    ): String

    @Throws(Exception::class)
    suspend fun rejectAuthentication(
        id: Long,
        reason: String,
    ): String?

    @Throws(Exception::class)
    suspend fun getRequest(
        id: Long,
    ): SessionRequest?

    @Throws(Exception::class)
    suspend fun approveRequest(
        id: Long,
        message: String,
    ): String?

    @Throws(Exception::class)
    suspend fun rejectRequest(
        id: Long,
        code: Int,
        message: String,
    ): String?

    @Throws(Exception::class)
    suspend fun getSession(
        sessionTopic: String,
    ): Session?

    @Throws(Exception::class)
    suspend fun updateSession(
        sessionTopic: String,
        chains: List<String>,
        accounts: List<String>,
        methods: List<String>,
        events: List<String>,
    )

    @Throws(Exception::class)
    suspend fun disconnectSession(
        sessionTopic: String,
    )
}

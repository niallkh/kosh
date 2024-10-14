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

    suspend fun pair(
        uri: String,
    ): ReownResult<Unit>

    suspend fun getProposal(
        pairingTopic: String,
    ): ReownResult<SessionProposal>

    suspend fun approveProposal(
        pairingTopic: String,
        chains: List<String>,
        accounts: List<String>,
        methods: List<String>,
        events: List<String>,
    ): ReownResult<StringWrapper>

    suspend fun rejectProposal(
        pairingTopic: String,
        reason: String,
    ): ReownResult<StringWrapper>

    suspend fun getAuthentication(
        id: Long,
    ): ReownResult<AuthenticationRequest>

    suspend fun approveAuthentication(
        id: Long,
        issuer: String,
        supportedChains: List<String>,
        supportedMethods: List<String>,
        signature: String,
    ): ReownResult<StringWrapper>

    suspend fun getAuthenticationMessage(
        id: Long,
        issuer: String,
        supportedChains: List<String>,
        supportedMethods: List<String>,
    ): ReownResult<StringWrapper>

    suspend fun rejectAuthentication(
        id: Long,
        reason: String,
    ): ReownResult<StringWrapper>

    suspend fun getRequest(
        id: Long,
    ): ReownResult<SessionRequest>

    suspend fun approveRequest(
        id: Long,
        message: String,
    ): ReownResult<StringWrapper>

    suspend fun rejectRequest(
        id: Long,
        code: Int,
        message: String,
    ): ReownResult<StringWrapper>

    suspend fun getSession(
        sessionTopic: String,
    ): ReownResult<Session>

    suspend fun updateSession(
        sessionTopic: String,
        chains: List<String>,
        accounts: List<String>,
        methods: List<String>,
        events: List<String>,
    ): ReownResult<Unit>

    suspend fun disconnectSession(
        sessionTopic: String,
    ): ReownResult<Unit>
}

package kosh.libs.reown

import com.reown.android.internal.common.signing.cacao.CacaoType
import com.reown.sign.client.Sign
import com.reown.walletkit.client.Wallet


internal fun mapProposal(
    proposal: Wallet.Model.SessionProposal,
    context: Wallet.Model.VerifyContext?,
) = SessionProposal(
    id = context?.id ?: proposal.proposerPublicKey.hashCode().toLong(),
    pairingTopic = proposal.pairingTopic,
    name = proposal.name,
    description = proposal.description,
    url = proposal.url,
    icon = proposal.icons.firstOrNull()?.toString(),
    verifyContext = mapContext(context),
    required = mapNamespace(proposal.requiredNamespaces[EIP155]),
    optional = mapNamespace(proposal.optionalNamespaces[EIP155]),
)


internal fun mapAuth(
    authenticate: Wallet.Model.SessionAuthenticate,
    context: Wallet.Model.VerifyContext?,
): AuthenticationRequest = AuthenticationRequest(
    id = authenticate.id,
    name = authenticate.participant.metadata?.name,
    description = authenticate.participant.metadata?.description,
    url = authenticate.participant.metadata?.url,
    icon = authenticate.participant.metadata?.icons?.firstOrNull()?.toString(),
    pairingTopic = authenticate.pairingTopic,
    verifyContext = mapContext(context)
)

internal fun mapRequest(
    request: Wallet.Model.SessionRequest,
    context: Wallet.Model.VerifyContext?,
): SessionRequest = SessionRequest(
    id = request.request.id,
    verifyContext = mapContext(context),
    sessionTopic = request.topic,
    name = request.peerMetaData?.name,
    description = request.peerMetaData?.description,
    url = request.peerMetaData?.url,
    icon = request.peerMetaData?.icons?.firstOrNull()?.toString(),
    chainId = request.chainId?.toULongOrNull(),
    method = request.request.method,
    params = request.request.params,
)

internal fun mapSession(
    session: Wallet.Model.Session,
): Session = Session(
    sessionTopic = session.topic,
    name = session.metaData?.name,
    description = session.metaData?.description,
    url = session.metaData?.url,
    icon = session.metaData?.icons?.firstOrNull()?.toString(),
    required = mapNamespace(session.requiredNamespaces[EIP155]),
    optional = mapNamespace((session.optionalNamespaces ?: mapOf())[EIP155]),
    approved = mapNamespace(session.namespaces[EIP155]),
)

internal fun mapNamespace(
    approvedNamespace: Wallet.Model.Namespace.Session?,
) = Session.Namespace(
    chains = approvedNamespace?.chains ?: listOf(),
    accounts = approvedNamespace?.accounts ?: listOf(),
    methods = approvedNamespace?.methods ?: listOf(),
    events = approvedNamespace?.events ?: listOf()
)

internal fun mapNamespace(
    approvedNamespace: Wallet.Model.Namespace.Proposal?,
) = SessionProposal.Namespace(
    chains = approvedNamespace?.chains ?: listOf(),
    methods = approvedNamespace?.methods ?: listOf(),
    events = approvedNamespace?.events ?: listOf()
)

internal fun mapContext(
    context: Wallet.Model.VerifyContext?,
): VerifyContext {
    if (context == null) return VerifyContext.Unverified
    return when (context.isScam) {
        true -> VerifyContext.Threat
        else -> when (context.validation) {
            Wallet.Model.Validation.VALID -> VerifyContext.Match
            Wallet.Model.Validation.INVALID -> VerifyContext.Mismatch
            Wallet.Model.Validation.UNKNOWN -> VerifyContext.Unverified
        }
    }
}

internal fun Sign.Model.SessionAuthenticate.toWallet(): Wallet.Model.SessionAuthenticate =
    Wallet.Model.SessionAuthenticate(id, topic, participant.toWallet(), payloadParams.toWallet())

internal fun Sign.Model.SessionAuthenticate.Participant.toWallet(): Wallet.Model.SessionAuthenticate.Participant =
    Wallet.Model.SessionAuthenticate.Participant(publicKey, metadata)

internal fun Sign.Model.PayloadParams.toWallet(): Wallet.Model.PayloadAuthRequestParams =
    Wallet.Model.PayloadAuthRequestParams(
        chains = chains,
        type = type ?: CacaoType.CAIP222.header,
        domain = domain,
        aud = aud,
        nonce = nonce,
        nbf = nbf,
        exp = exp,
        statement = statement,
        requestId = requestId,
        resources = resources,
        iat = iat
    )

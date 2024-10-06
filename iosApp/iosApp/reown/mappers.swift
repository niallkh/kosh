import App
import ReownWalletKit

func mapProposal(
    proposal: ReownWalletKit.Session.Proposal,
    context: ReownWalletKit.VerifyContext?
) -> App.SessionProposal {
    return App.SessionProposal(
        id: Int64(proposal.id.hashValue),
        pairingTopic: proposal.pairingTopic,
        name: proposal.proposer.name,
        url: proposal.proposer.url,
        icon: proposal.proposer.icons.first,
        description: proposal.proposer.description,
        verifyContext: mapContext(context: context),
        required: mapProposalNamespace(namespace: proposal.requiredNamespaces[ReownAdapterKt.EIP155]),
        optional: mapProposalNamespace(namespace: proposal.optionalNamespaces?[ReownAdapterKt.EIP155])
    )
}

func mapAuthentication(
    authentication: ReownWalletKit.AuthenticationRequest,
    context: ReownWalletKit.VerifyContext?
) -> App.AuthenticationRequest {
    return App.AuthenticationRequest(
        id: authentication.id.integer,
        pairingTopic: authentication.topic,
        name: authentication.requester.name,
        url: authentication.requester.url,
        icon: authentication.requester.icons.first,
        description: authentication.requester.description,
        verifyContext: mapContext(context: context)
    )
}

func mapRequest(
    request: ReownWalletKit.Request,
    session: ReownWalletKit.Session,
    context: ReownWalletKit.VerifyContext?
) -> App.SessionRequest {
    return App.SessionRequest(
        id: request.id.integer,
        sessionTopic: request.topic,
        name: session.peer.name,
        url: session.peer.url,
        icon: session.peer.icons.first,
        description: session.peer.description,
        chainId: KotlinULong(value: UInt64(request.chainId.reference)!),
        method: request.method,
        params: request.params.stringRepresentation,
        verifyContext: mapContext(context: context)
    )
}

func mapSession(
    session: ReownWalletKit.Session
) -> App.Session {
    return App.Session(
        sessionTopic: session.topic,
        name: session.peer.name,
        url: session.peer.url,
        icon: session.peer.icons.first,
        description: session.peer.description,
        required: mapProposalNamespace(namespace: session.requiredNamespaces[ReownAdapterKt.EIP155]),
        optional: mapProposalNamespace(namespace: nil),
        approved: mapSessionNamespace(namespace: session.namespaces[ReownAdapterKt.EIP155])
    )
}

func mapProposalNamespace(
    namespace : ProposalNamespace?
) -> App.SessionProposal.Namespace {
    return App.SessionProposal.Namespace(
        chains: namespace?.chains?.map({ch in ch.absoluteString}) ?? [],
        methods: Array(namespace?.methods ?? []),
        events: Array(namespace?.events ?? [])
    )
}

func mapSessionNamespace(
    namespace : SessionNamespace?
) -> App.Session.Namespace {
    return App.Session.Namespace(
        chains: namespace?.chains?.map({ch in ch.absoluteString}) ?? [],
        accounts: namespace?.accounts.map({acc in acc.absoluteString}) ?? [],
        methods: Array(namespace?.methods ?? []),
        events: Array(namespace?.events ?? [])
    )
}

func mapContext(
    context: ReownWalletKit.VerifyContext?
) -> App.VerifyContext {
    return switch context?.validation {
    case .some(ReownWalletKit.VerifyContext.ValidationStatus.invalid):
        App.VerifyContext.mismatch
    case .some(ReownWalletKit.VerifyContext.ValidationStatus.scam):
        App.VerifyContext.threat
    case .some(ReownWalletKit.VerifyContext.ValidationStatus.unknown):
        App.VerifyContext.unverified
    case .some(ReownWalletKit.VerifyContext.ValidationStatus.valid):
        App.VerifyContext.match
    case .none:
        App.VerifyContext.unverified
    }
}


import App
import ReownWalletKit
import Starscream
import WalletConnectPairing
import Combine


enum ReownAdapterError: Error {
    case expiry
}

class IosReownAdapter: ReownAdapter {
    let projectId: String
    
    private let authenticationsPublisher = CurrentValueSubject<[(App.AuthenticationRequest)], Never>([])
    private let requestsPublisher = CurrentValueSubject<[(App.SessionRequest)], Never>([])
    private let sessionsPublisher = CurrentValueSubject<[(App.Session)], Never>([])
    
    init(projectId: String) {
        self.projectId = projectId
    }
    
    func initialize() async {
        log { "initialize()" }
        
        let metadata = AppMetadata(
            name: "Kosh",
            description: "Bridge the gap between your hardware wallet and dapps. Manage your crypto and interact with dapp from your mobile device.",
            url: "https://kosh.eth.limo",
            icons: ["https://kosh.eth.limo/images/icon.webp"],
            redirect: try! AppMetadata.Redirect(
                native: "kosh://request",
                universal: "https://kosh.eth.limo",
                linkMode: false
            )
        )
        
        Networking.configure(
            groupIdentifier: "group.eth.kosh.app",
            projectId: self.projectId,
            socketFactory: DefaultSocketFactory()
        )
        
        WalletKit.configure(
            metadata: metadata,
            crypto: DefaultCryptoProvider()
        )
        
    
        let _ = WalletKit.instance.authenticateRequestPublisher
            .sink { _ in
                self.log { "new auth arrived" }
                self.updateAuthentications()
            }
        
        let _ = WalletKit.instance.sessionRequestPublisher
            .sink { _ in
                self.log { "new request arrived" }
                self.updateRequests()
            }
        
        let _ = WalletKit.instance.requestExpirationPublisher
            .sink { _ in
                self.log { "new request expired" }
                self.updateRequests()
            }
        
        let _ = WalletKit.instance.sessionsPublisher
            .sink { _ in
                self.log { "new sessions updated" }
                self.updateSessions()
            }
        
        let _ = WalletKit.instance.sessionSettlePublisher
            .sink { _ in
                self.log { "new session settled" }
                self.updateSessions()
            }
        
        let _ = WalletKit.instance.sessionDeletePublisher
            .sink { _ in
                self.log { "new session deleted" }
                self.updateSessions()
            }
        
        let _ = WalletKit.instance.sessionResponsePublisher
            .sink { _ in
                self.log { "new session response published" }
                self.updateSessions()
            }
    }
    
    func connect() async {
        try! Networking.instance.connect()
    }
    
    func disconnect() async {
        try! Networking.instance.disconnect(
            closeCode: URLSessionWebSocketTask.CloseCode.normalClosure
        )
    }
    
    func getNewProposal(callback: @escaping (App.SessionProposal) -> Void) -> () -> Void {
        let sub = WalletKit.instance.sessionProposalPublisher
            .map(mapProposal)
            .sink(receiveValue: callback)
        
        return { sub.cancel() }
    }
    
    
    func getProposals(callback: @escaping ([App.SessionProposal]) -> Void) -> () -> Void {
        let sub = WalletKit.instance.pendingProposalsPublisher
            .map { (proposals) in
                proposals.map { (proposal, context) in
                    mapProposal(proposal: proposal, context: context)
                }
            }
            .sink(receiveValue: callback)
        
        return { sub.cancel() }
        
    }
    
    func getNewAuthentication(callback: @escaping (App.AuthenticationRequest) -> Void) -> () -> Void {
        let sub = WalletKit.instance.authenticateRequestPublisher
            .map(mapAuthentication)
            .sink(receiveValue: callback)
        
        return { sub.cancel() }
    }
    
    func getAuthentications(callback: @escaping ([App.AuthenticationRequest]) -> Void) -> () -> Void {
        let sub = authenticationsPublisher
            .sink(receiveValue: callback)
        
        self.updateAuthentications()
        
        return { sub.cancel() }
    }
    
    func getNewRequest(callback: @escaping (App.SessionRequest) -> Void) -> () -> Void {
        let sub = WalletKit.instance.sessionRequestPublisher
            .compactMap { (request, context) in
                guard let session = self.getSession(topic: request.topic) else {
                    return nil
                }
                
                return mapRequest(request: request, session: session, context: context)
            }
            .sink(receiveValue: callback)
        
        return { sub.cancel() }
    }
    
    func getRequests(callback: @escaping ([App.SessionRequest]) -> Void) -> () -> Void {
        let sub = requestsPublisher
            .sink(receiveValue: callback)
        
        self.updateRequests()
        
        return { sub.cancel() }
    }
    
    func getSessions(callback: @escaping ([App.Session]) -> Void) -> () -> Void {
        log { "getSessions()" }
        
        let sub =  sessionsPublisher
            .sink(receiveValue: callback)
        
        self.updateSessions()
        
        return { sub.cancel() }
    }
    
    func pair(uri: String) async -> ReownResult<KotlinUnit> {
        do {
            let wcUri = try WalletConnectURI.init(uriString: uri)
            try await WalletKit.instance.pair(uri: wcUri)
            return ReownResultSuccess<KotlinUnit>.companion.invoke()
        } catch {
            return ReownResultFailure<KotlinUnit>(value: ReownFailure.Other(message: error.localizedDescription))
        }
    }
    
    func getProposal(
        pairingTopic: String
    ) async -> ReownResult<App.SessionProposal> {
        let found = WalletKit.instance.getPendingProposals().first { (prop, ctx) in prop.pairingTopic == pairingTopic }
        guard let found = found else {
            return ReownResultFailure<App.SessionProposal>(value: ReownFailure.NotFound(message: nil))
        }
        return ReownResultSuccess(value: mapProposal(proposal: found.proposal, context: found.context))
    }
    
    func approveProposal(
        pairingTopic: String,
        chains: [String],
        accounts: [String],
        methods: [String],
        events: [String]
    ) async -> ReownResult<StringWrapper> {
        do {
            let found = WalletKit.instance.getPendingProposals().first { (prop, ctx) in prop.pairingTopic == pairingTopic }
            guard let proposal = found?.proposal else {
                return ReownResultFailure<StringWrapper>(value: ReownFailure.NotFound(message: nil))
            }
            
            let _ = try await WalletKit.instance.approve(
                proposalId: proposal.id,
                namespaces: [
                    ReownAdapterKt.EIP155: SessionNamespace(
                        chains: chains.map { ch in Blockchain(ch)! },
                        accounts: accounts.map { ac in Account(ac)! },
                        methods: Set(methods),
                        events: Set(events)
                    )
                ]
            )
            
            self.updateSessions()
            
            return ReownResultSuccess(value: StringWrapper(value: proposal.proposer.redirect?.native ?? ""))
        } catch {
            return ReownResultFailure<StringWrapper>(value: ReownFailure.Other(message: error.localizedDescription))
        }
    }
    
    func rejectProposal(
        pairingTopic: String,
        reason: String
    ) async -> ReownResult<StringWrapper> {
        do {
            let found = WalletKit.instance.getPendingProposals().first { (prop, ctx) in prop.pairingTopic == pairingTopic }
            guard let proposal = found?.proposal else {
                return ReownResultFailure<StringWrapper>(value: ReownFailure.NotFound(message: nil))
            }
            
            try await WalletKit.instance.rejectSession(
                proposalId: proposal.id,
                reason: RejectionReason.userRejected
            )
                        
            return ReownResultSuccess(value: StringWrapper(value: proposal.proposer.redirect?.native ?? ""))
        } catch {
            return ReownResultFailure<StringWrapper>(value: ReownFailure.Other(message: error.localizedDescription))
        }
    }
    
    func getAuthentication(
        id: Int64
    ) async -> ReownResult<App.AuthenticationRequest> {
        do {
            let found = try WalletKit.instance.getPendingAuthRequests().first { (auth, ctx) in auth.id.integer == id }
            guard let found = found else {
                return ReownResultFailure<App.AuthenticationRequest>(value: ReownFailure.NotFound(message: nil))
            }
            return ReownResultSuccess(value: mapAuthentication(authentication: found.0, context: found.1))
        } catch {
            return ReownResultFailure<App.AuthenticationRequest>(value: ReownFailure.Other(message: error.localizedDescription))
        }
    }
    
    func approveAuthentication(
        id: Int64,
        issuer: String,
        supportedChains: [String],
        supportedMethods: [String],
        signature: String
    ) async -> ReownResult<StringWrapper> {
        do {
            let found = try WalletKit.instance.getPendingAuthRequests().first { (auth, ctx) in auth.id.integer == id }
            guard let auth = found?.0 else {
                return ReownResultFailure<StringWrapper>(value: ReownFailure.NotFound(message: nil))
            }
            
            let authPayload = try WalletKit.instance.buildAuthPayload(
                payload: auth.payload,
                supportedEVMChains: supportedChains.map { ch in Blockchain(ch)! },
                supportedMethods: supportedMethods
            )
            
            let authObject = try WalletKit.instance.buildSignedAuthObject(
                authPayload: authPayload,
                signature: CacaoSignature(t: CacaoSignatureType.eip191, s: signature),
                account: Account.init(DIDPKHString: issuer)
            )
            
            let _ = try await WalletKit.instance.approveSessionAuthenticate(
                requestId: RPCID(id),
                auths: [authObject]
            )
            
            self.updateAuthentications()
            
            return ReownResultSuccess(value: StringWrapper(value: auth.requester.redirect?.native ?? ""))
        } catch {
            return ReownResultFailure<StringWrapper>(value: ReownFailure.Other(message: error.localizedDescription))
        }
    }
    
    func getAuthenticationMessage(
        id: Int64,
        issuer: String,
        supportedChains: [String],
        supportedMethods: [String]
    ) async -> ReownResult<StringWrapper> {
        do {
            let found = try WalletKit.instance.getPendingAuthRequests().first { (auth, ctx) in auth.id.integer == id }
            guard let auth = found?.0 else {
                return ReownResultFailure<StringWrapper>(value: ReownFailure.NotFound(message: nil))
            }
            
            let authPayload = try WalletKit.instance.buildAuthPayload(
                payload: auth.payload,
                supportedEVMChains: supportedChains.map { ch in Blockchain(ch)! },
                supportedMethods: supportedMethods
            )
            
            let msg = try WalletKit.instance.formatAuthMessage(
                payload: authPayload,
                account: Account.init(DIDPKHString: issuer)
            )
            
            return ReownResultSuccess(value: StringWrapper(value: msg))
        } catch {
            return ReownResultFailure<StringWrapper>(value: ReownFailure.Other(message: error.localizedDescription))
        }
    }
    
    func rejectAuthentication(
        id: Int64,
        reason: String
    ) async -> ReownResult<StringWrapper> {
        do {
            let found = try WalletKit.instance.getPendingAuthRequests().first { (auth, ctx) in auth.id.integer == id }
            guard let auth = found?.0 else {
                return ReownResultFailure<StringWrapper>(value: ReownFailure.NotFound(message: nil))
            }
            
            try await WalletKit.instance.rejectSession(requestId: RPCID(id))
            
            self.updateAuthentications()
            
            return ReownResultSuccess(value: StringWrapper(value: auth.requester.redirect?.native ?? ""))
        } catch {
            return ReownResultFailure<StringWrapper>(value: ReownFailure.Other(message: error.localizedDescription))
        }
    }
    
    func getRequest(
        id: Int64
    ) async -> ReownResult<App.SessionRequest> {
        let found = WalletKit.instance.getPendingRequests().first { (req, ctx) in req.id.integer == id }
        guard let found = found else {
            return ReownResultFailure<App.SessionRequest>(value: ReownFailure.NotFound(message: nil))
        }
        guard let session = self.getSession(topic: found.request.topic) else {
            return ReownResultFailure<App.SessionRequest>(value: ReownFailure.NotFound(message: nil))
        }
        let request = mapRequest(request: found.request, session: session, context: found.context)
        return ReownResultSuccess(value: request)
    }
    
    func approveRequest(
        id: Int64,
        message: String
    ) async -> ReownResult<StringWrapper> {
        do {
            let found = WalletKit.instance.getPendingRequests().first { (req, ctx) in req.id.integer == id }
            guard let req = found?.0 else {
                return ReownResultFailure<StringWrapper>(value: ReownFailure.NotFound(message: nil))
            }
            guard let session = self.getSession(topic: req.topic) else {
                return ReownResultFailure<StringWrapper>(value: ReownFailure.NotFound(message: nil))
            }
            
            try await WalletKit.instance.respond(
                topic: req.topic,
                requestId: req.id,
                response: .response(AnyCodable(message))
            )
            
            self.updateRequests()
            
            return ReownResultSuccess(value: StringWrapper(value: session.peer.redirect?.native ?? ""))
        } catch {
            return ReownResultFailure<StringWrapper>(value: ReownFailure.Other(message: error.localizedDescription))
        }
    }
    
    func rejectRequest(
        id: Int64,
        code: Int32,
        message: String
    ) async -> ReownResult<StringWrapper> {
        do {
            let found = WalletKit.instance.getPendingRequests().first { (req, ctx) in req.id.integer == id }
            guard let req = found?.0 else {
                return ReownResultFailure<StringWrapper>(value: ReownFailure.NotFound(message: nil))
            }
            guard let session = self.getSession(topic: req.topic) else {
                return ReownResultFailure<StringWrapper>(value: ReownFailure.NotFound(message: nil))
            }
            
            try await WalletKit.instance.respond(
                topic: req.topic,
                requestId: req.id,
                response: .error(JSONRPCError(code: Int(code), message: message))
            )
            
            self.updateRequests()
            
            return ReownResultSuccess(value: StringWrapper(value: session.peer.redirect?.native ?? ""))
        } catch {
            return ReownResultFailure<StringWrapper>(value: ReownFailure.Other(message: error.localizedDescription))
        }
    }
    
    func getSession(
        sessionTopic: String
    ) async -> ReownResult<App.Session> {
        guard let session = self.getSession(topic: sessionTopic) else {
            return ReownResultFailure<App.Session>(value: ReownFailure.NotFound(message: nil))
        }
        return ReownResultSuccess(value: mapSession(session: session))
    }
    
    func updateSession(
        sessionTopic: String,
        chains: [String],
        accounts: [String],
        methods: [String],
        events: [String]
    ) async -> ReownResult<KotlinUnit> {
        do {
            try await WalletKit.instance.update(
                topic: sessionTopic,
                namespaces:  [
                    ReownAdapterKt.EIP155: SessionNamespace(
                        chains: chains.map { ch in Blockchain(ch)! },
                        accounts: accounts.map { ac in Account(ac)! },
                        methods: Set(methods),
                        events: Set(events)
                    )
                ]
            )
            
            self.updateSessions()
           
            return ReownResultSuccess<KotlinUnit>.companion.invoke()
        } catch {
            return ReownResultFailure<KotlinUnit>(value: ReownFailure.Other(message: error.localizedDescription))
        }
    }
    
    func disconnectSession(sessionTopic: String) async -> ReownResult<KotlinUnit> {
        do {
            try await WalletKit.instance.disconnect(topic: sessionTopic)
            
            self.updateSessions()
            
            return ReownResultSuccess<KotlinUnit>.companion.invoke()
        } catch {
            return ReownResultFailure<KotlinUnit>(value: ReownFailure.Other(message: error.localizedDescription))

        }
    }
    
    private func getSession(topic: String) -> ReownWalletKit.Session? {
        return WalletKit.instance.getSessions().first(where: { session in session.topic == topic })
    }
    
    private func updateAuthentications() {
        do {
            authenticationsPublisher.send(
                try  WalletKit.instance.getPendingAuthRequests().map { (authentication, context) in
                    mapAuthentication(authentication: authentication, context: context)
                }
            )
        } catch {
        }
    }
    
    private func updateRequests() {
        requestsPublisher.send(
            WalletKit.instance.getPendingRequests().compactMap { (request, context) in
                guard let session = self.getSession(topic: request.topic) else {
                    return nil as App.SessionRequest?
                }
                
                return mapRequest(request: request, session: session, context: context)
            }
        )
    }
    
    private func updateSessions() {
        sessionsPublisher.send(
            WalletKit.instance.getSessions().map { (session) in
                mapSession(session: session)
            }
        )
    }
    
    func log(message: @escaping () -> String) {
        KermitLogger.companion.v(throwable: nil, tag: "[K]IosReownAdapter", message: message)
    }
}

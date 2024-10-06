import App
import ReownWalletKit
import Starscream
import WalletConnectPairing
import Combine


enum ReownAdapterError: Error {
    case proposalNotFound
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
        let metadata = AppMetadata(
            name: "Kosh",
            description: "Bridge the gap between your hardware wallet and dapps. Manage your crypto and interact with dapp from your mobile device.",
            url: "https://kosh.eth.limo",
            icons: ["https://kosh.eth.limo/images/icon.webp"],
            redirect: try! AppMetadata.Redirect(
                native: "kosh://request",
                universal: "https://kosh.eth.limo/app",
                linkMode: true
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
            .sink { _ in self.updateAuthentications() }
        
        let _ = WalletKit.instance.sessionRequestPublisher
            .sink { _ in self.updateRequests() }
        
        let _ = WalletKit.instance.requestExpirationPublisher
            .sink { _ in self.updateRequests() }
        
        let _ = WalletKit.instance.sessionsPublisher
            .sink { _ in self.updateSessions() }
        
        let _ = WalletKit.instance.sessionSettlePublisher
            .sink { _ in self.updateSessions() }
        
        let _ = WalletKit.instance.sessionDeletePublisher
            .sink { _ in self.updateSessions() }
        
        let _ = WalletKit.instance.sessionResponsePublisher
            .sink { _ in self.updateSessions() }
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
            .receive(on: DispatchQueue.main)
            .sink { (proposal, context) in
                callback(mapProposal(proposal: proposal, context: context))
            }
        
        return { sub.cancel() }
    }
    
    
    func getProposals(callback: @escaping ([App.SessionProposal]) -> Void) -> () -> Void {
        let sub = WalletKit.instance.pendingProposalsPublisher
            .receive(on: DispatchQueue.main)
            .sink { (proposals) in
                callback(proposals.map{ (proposal, context) in
                    mapProposal(proposal: proposal, context: context)
                })
            }
        
        return { sub.cancel() }
        
    }
    
    func getNewAuthentication(callback: @escaping (App.AuthenticationRequest) -> Void) -> () -> Void {
        let sub = WalletKit.instance.authenticateRequestPublisher
            .receive(on: DispatchQueue.main)
            .sink { (authenticate, context) in
                callback(mapAuthentication(authentication: authenticate, context: context))
            }
        
        return { sub.cancel() }
    }
    
    func getAuthentications(callback: @escaping ([App.AuthenticationRequest]) -> Void) -> () -> Void {
        let sub = authenticationsPublisher
            .receive(on: DispatchQueue.main)
            .sink { (authentications) in
                callback(authentications)
            }
        
        self.updateAuthentications()
        
        return { sub.cancel() }
    }
    
    func getNewRequest(callback: @escaping (App.SessionRequest) -> Void) -> () -> Void {
        let sub = WalletKit.instance.sessionRequestPublisher
            .receive(on: DispatchQueue.main)
            .sink { (request, context) in
                guard let session = self.getSession(topic: request.topic) else {
                    return
                }
                
                callback(mapRequest(request: request, session: session, context: context))
            }
        
        
        return { sub.cancel() }
    }
    
    func getRequests(callback: @escaping ([App.SessionRequest]) -> Void) -> () -> Void {
        let sub = requestsPublisher
            .receive(on: DispatchQueue.main)
            .sink { (requests) in
                callback(requests)
            }
        
        self.updateRequests()
        
        return { sub.cancel() }
    }
    
    func getSessions(callback: @escaping ([App.Session]) -> Void) -> () -> Void {
        log { "getSessions()" }
        let sub =  sessionsPublisher
            .receive(on: DispatchQueue.main)
            .sink(receiveValue: callback)
        
        self.updateSessions()

        return { sub.cancel() }
    }
    
    func pair(uri: String) async throws {
        let wcUri = try WalletConnectURI.init(uriString: uri)
        try await WalletKit.instance.pair(uri: wcUri)
    }
    
    func getProposal(
        pairingTopic: String
    ) async throws -> App.SessionProposal? {
        let found = WalletKit.instance.getPendingProposals().first { (prop, ctx) in prop.pairingTopic == pairingTopic }
        guard let found = found else {
            return nil
        }
        return mapProposal(proposal: found.proposal, context: found.context)
    }
    
    func approveProposal(
        pairingTopic: String,
        chains: [String],
        accounts: [String],
        methods: [String],
        events: [String]
    ) async throws {
        let found = WalletKit.instance.getPendingProposals().first { (prop, ctx) in prop.pairingTopic == pairingTopic }
        guard let proposal = found?.proposal else {
            throw ReownAdapterError.proposalNotFound
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
    }
    
    func rejectProposal(
        pairingTopic: String,
        reason: String
    ) async throws {
        let found = WalletKit.instance.getPendingProposals().first { (prop, ctx) in prop.pairingTopic == pairingTopic }
        guard let proposal = found?.proposal else {
            throw ReownAdapterError.proposalNotFound
        }
        
        try await WalletKit.instance.rejectSession(
            proposalId: proposal.id,
            reason: RejectionReason.userRejected
        )
    }
    
    func getAuthentication(
        id: Int64
    ) async throws -> App.AuthenticationRequest? {
        let found = try WalletKit.instance.getPendingAuthRequests().first { (auth, ctx) in auth.id.integer == id }
        guard let found = found else {
            return nil
        }
        return mapAuthentication(authentication: found.0, context: found.1)
    }
    
    func approveAuthentication(
        id: Int64,
        issuer: String,
        supportedChains: [String],
        supportedMethods: [String],
        signature: String
    ) async throws {
        let found = try WalletKit.instance.getPendingAuthRequests().first { (auth, ctx) in auth.id.integer == id }
        guard let auth = found?.0 else {
            throw ReownAdapterError.proposalNotFound
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
    }
    
    func getAuthenticationMessage(
        id: Int64,
        issuer: String,
        supportedChains: [String],
        supportedMethods: [String]
    ) async throws -> String {
        let found = try WalletKit.instance.getPendingAuthRequests().first { (auth, ctx) in auth.id.integer == id }
        guard let auth = found?.0 else {
            throw ReownAdapterError.proposalNotFound
        }
        
        let authPayload = try WalletKit.instance.buildAuthPayload(
            payload: auth.payload,
            supportedEVMChains: supportedChains.map { ch in Blockchain(ch)! },
            supportedMethods: supportedMethods
        )
        
        return try WalletKit.instance.formatAuthMessage(
            payload: authPayload,
            account: Account.init(DIDPKHString: issuer)
        )
    }
    
    func rejectAuthentication(
        id: Int64,
        reason: String
    ) async throws {
        try await WalletKit.instance.rejectSession(requestId: RPCID(id))
        
        self.updateAuthentications()
    }
    
    func getRequest(
        id: Int64
    ) async throws -> App.SessionRequest? {
        let found = WalletKit.instance.getPendingRequests().first { (req, ctx) in req.id.integer == id }
        guard let found = found else {
            return nil
        }
        guard let session = self.getSession(topic: found.request.topic) else {
            return nil
        }
        return mapRequest(request: found.request, session: session, context: found.context)
    }
    
    func approveRequest(
        id: Int64,
        message: String
    ) async throws {
        let found = WalletKit.instance.getPendingRequests().first { (req, ctx) in req.id.integer == id }
        guard let req = found?.0 else {
            throw ReownAdapterError.proposalNotFound
        }
        
        try await WalletKit.instance.respond(
            topic: req.topic,
            requestId: req.id,
            response: .response(AnyCodable(message))
        )
        
        self.updateRequests()
    }
    
    func rejectRequest(
        id: Int64,
        code: Int32,
        message: String
    ) async throws {
        let found = WalletKit.instance.getPendingRequests().first { (req, ctx) in req.id.integer == id }
        guard let req = found?.0 else {
            throw ReownAdapterError.proposalNotFound
        }
        
        try await WalletKit.instance.respond(
            topic: req.topic,
            requestId: req.id,
            response: .error(JSONRPCError(code: Int(code), message: message))
        )
        
        self.updateRequests()
    }
    
    func getSession(
        sessionTopic: String
    ) async throws -> App.Session? {
        guard let session = self.getSession(topic: sessionTopic) else {
            return nil
        }
        return mapSession(session: session)
    }
    
    func updateSession(
        sessionTopic: String,
        chains: [String],
        accounts: [String],
        methods: [String],
        events: [String]
    ) async throws {
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
    }
    
    func disconnectSession(sessionTopic: String) async throws {
        try await WalletKit.instance.disconnect(topic: sessionTopic)
        
        self.updateSessions()
    }
    
    func getSession(topic: String) -> ReownWalletKit.Session? {
        return WalletKit.instance.getSessions().first(where: { session in session.topic == topic })
    }
    
    func updateAuthentications() {
        do {
            authenticationsPublisher.send(
                try  WalletKit.instance.getPendingAuthRequests().map { (authentication, context) in
                    mapAuthentication(authentication: authentication, context: context)
                }
            )
        } catch {
        }
    }
    
    func updateRequests() {
        requestsPublisher.send(
            WalletKit.instance.getPendingRequests().compactMap { (request, context) in
                guard let session = self.getSession(topic: request.topic) else {
                    return nil as App.SessionRequest?
                }
                
                return mapRequest(request: request, session: session, context: context)
            }
        )
    }
    
    func updateSessions() {
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

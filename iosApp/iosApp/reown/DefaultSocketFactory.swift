import Starscream
import Foundation
import WalletConnectRelay

extension WebSocket: @retroactive WebSocketConnecting { }

struct DefaultSocketFactory: WebSocketFactory {
    func create(with url: URL) -> WebSocketConnecting {
        var request = URLRequest(url: url)
        request.timeoutInterval = 5
        
        return WebSocket(
            request: URLRequest(url: url),
            protocols: []
        )
    }
}

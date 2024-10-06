import Starscream
import Foundation
import WalletConnectRelay

extension WebSocket: @retroactive WebSocketConnecting { }

struct DefaultSocketFactory: WebSocketFactory {
    func create(with url: URL) -> WebSocketConnecting {
        return WebSocket(url: url)
    }
}

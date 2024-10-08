import UIKit
import SwiftUI
import App


class AppDelegate: NSObject, UIApplicationDelegate {
    let scope: ApplicationScope = IosApplicationScope(
        reownComponent: IosReownComponent(
            reownAdapter: IosReownAdapter(projectId: Bundle.main.object(forInfoDictionaryKey: "REOWN_PROJECT") as! String)
        )
    )
}

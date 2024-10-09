import UIKit
import SwiftUI
import App


class AppDelegate: NSObject, UIApplicationDelegate {
    let appScope: AppScope
    let windowScope: WindowScope
    
    override init() {
        appScope = IosAppScope(
            reownComponent: IosReownComponent(
                reownAdapter: IosReownAdapter(projectId: Bundle.main.object(forInfoDictionaryKey: "REOWN_PROJECT") as! String)
            )
        )
        windowScope = IosWindowScope(
            applicationScope: appScope,
            lifecycle: ApplicationLifecycle()
        )
    }
    
    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil
    ) -> Bool {
        return true
    }
}

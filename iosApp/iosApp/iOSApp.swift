import SwiftUI
import App

@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self)
    var appDelegate: AppDelegate
    
    @Environment(\.scenePhase)
    var scenePhase: ScenePhase

    var body: some Scene {
        WindowGroup {
            RootView(scope: appDelegate.scope)
            .ignoresSafeArea()
        }
    }
}

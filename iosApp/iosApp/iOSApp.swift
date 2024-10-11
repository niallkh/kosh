import SwiftUI
import App

@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self)
    var appDelegate: AppDelegate
    
    var body: some Scene {
        WindowGroup {
            RootView(
                appScope: appDelegate.appScope!,
                uiContext: appDelegate.uiContext!
            )
                .ignoresSafeArea()
                .onOpenURL { url in
                    appDelegate.uiContext!.uiScope.deeplinkHandler.handle(url: url.absoluteString)
                }
        }
    }
}

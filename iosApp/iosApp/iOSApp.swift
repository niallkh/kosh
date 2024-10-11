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
                windowScope: appDelegate.windowScope!
            )
                .ignoresSafeArea()
                .onOpenURL { url in
                    appDelegate.windowScope!.deeplinkHandler.handle(url: url.absoluteString)
                }
        }
    }
}

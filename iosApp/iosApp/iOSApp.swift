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
                presentationContext: appDelegate.presentationContext!
            )
                .ignoresSafeArea()
                .onOpenURL { url in
                    appDelegate.presentationContext!.presentationScope.deeplinkHandler.handle(url: url.absoluteString)
                }
        }
    }
}

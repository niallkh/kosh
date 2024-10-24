import UIKit
import SwiftUI
import App
import Bugsnag
import BugsnagPerformance

class AppDelegate: NSObject, UIApplicationDelegate {
    var appScope: AppScope? = nil
    var uiContext: PresentationUiContext? = nil
    var stateKeeper: StateKeeperDispatcher? = nil
    
    func application(_ application: UIApplication, shouldRestoreSecureApplicationState coder: NSCoder) -> Bool {
        print("[K]shouldRestoreSecureApplicationState")
        stateKeeper = StateKeeperDispatcherKt.StateKeeperDispatcher(savedState: StateKeeperKt.restore(coder: coder))
        return true
    }
    
    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil
    ) -> Bool {
        print("[K]didFinishLaunchingWithOptions")
        
        appScope = IosAppScope(
            reownComponent: IosReownComponent(
                reownAdapter: IosReownAdapter(projectId: Bundle.main.object(forInfoDictionaryKey: "REOWN_PROJECT") as! String)
            ),
            bugsnagConfiguration: BugsnagConfiguration.loadConfig()
        )
        
        self.stateKeeper = self.stateKeeper ?? StateKeeperDispatcherKt.StateKeeperDispatcher(savedState: nil)
        
        uiContext = IosUiContextKt.iosUiContext(
            appScope: appScope!,
            lifecycle: ApplicationLifecycle(),
            stateKeeper: stateKeeper!
        )
        
        return true
    }
    
    func application(_ application: UIApplication, shouldSaveSecureApplicationState coder: NSCoder) -> Bool {
        print("[K]shouldSaveSecureApplicationState")
        StateKeeperKt.save(coder: coder, state: stateKeeper!.save())
        return true
    }
}


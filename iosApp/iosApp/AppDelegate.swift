import UIKit
import SwiftUI
import App
import Firebase

class AppDelegate: NSObject, UIApplicationDelegate {
    var appScope: AppScope? = nil
    var presentationContext: PresentationContext? = nil
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
        
        FirebaseApp.configure()
        
        appScope = IosAppScope(
            reownComponent: IosReownComponent(
                reownAdapter: IosReownAdapter(projectId: Bundle.main.object(forInfoDictionaryKey: "REOWN_PROJECT") as! String)
            )
        )
        
        self.stateKeeper = self.stateKeeper ?? StateKeeperDispatcherKt.StateKeeperDispatcher(savedState: nil)
        
        presentationContext = IosPresentationContextKt.iosPresentationContext(
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


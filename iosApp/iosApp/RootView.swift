import UIKit
import SwiftUI
import App

class AppDelegate: NSObject, UIApplicationDelegate {
    let root: ComponentContext = DefaultComponentContext(lifecycle: ApplicationLifecycle())
}

struct RootView: UIViewControllerRepresentable {
    let root: ComponentContext

    func makeUIViewController(context: Context) -> UIViewController {
        RootViewControllerKt.rootViewController(root: root)
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

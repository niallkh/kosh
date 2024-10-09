import UIKit
import SwiftUI
import App

struct RootView: UIViewControllerRepresentable {
    let appScope: AppScope
    let windowScope: WindowScope

    func makeUIViewController(context: Context) -> UIViewController {                
        return RootViewControllerKt.rootViewController(
            appScope: appScope,
            windowScope: windowScope
        )
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
    }
}

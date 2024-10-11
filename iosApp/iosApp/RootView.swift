import UIKit
import SwiftUI
import App

struct RootView: UIViewControllerRepresentable {
    let appScope: AppScope
    var uiContext: PresentationUiContext

    func makeUIViewController(context: Context) -> UIViewController {                
        return RootViewControllerKt.rootViewController(
            appScope: appScope,
            uiContext: uiContext
        )
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
    }
}

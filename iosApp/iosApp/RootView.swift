import UIKit
import SwiftUI
import App

struct RootView: UIViewControllerRepresentable {
    let appScope: AppScope
    var presentationContext: PresentationContext

    func makeUIViewController(context: Context) -> UIViewController {                
        return RootViewControllerKt.rootViewController(
            appScope: appScope,
            presentationContext: presentationContext
        )
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
    }
}

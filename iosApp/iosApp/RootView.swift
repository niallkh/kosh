import UIKit
import SwiftUI
import App

struct RootView: UIViewControllerRepresentable {
    let scope: ApplicationScope

    func makeUIViewController(context: Context) -> UIViewController {
        RootViewControllerKt.rootViewController(
            applicationScope: scope
        )
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

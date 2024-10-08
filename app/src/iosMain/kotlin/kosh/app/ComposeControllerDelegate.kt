package kosh.app

import androidx.compose.ui.uikit.ComposeUIViewControllerDelegate
import androidx.lifecycle.ViewModel
import com.arkivanov.essenty.lifecycle.LifecycleRegistry

class ComposeControllerDelegate : ComposeUIViewControllerDelegate, ViewModel() {
    val registry = LifecycleRegistry()

    override fun viewDidLoad() {
        registry.onCreate()
    }

    override fun viewWillAppear(animated: Boolean) {
        registry.onStart()
    }

    override fun viewDidAppear(animated: Boolean) {
        registry.onResume()
    }

    override fun viewWillDisappear(animated: Boolean) {
        registry.onPause()
    }

    override fun viewDidDisappear(animated: Boolean) {
        registry.onStop()
    }

    override fun onCleared() {
        registry.onDestroy()
    }
}

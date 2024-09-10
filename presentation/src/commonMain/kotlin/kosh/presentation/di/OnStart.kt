package kosh.presentation.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.arkivanov.essenty.lifecycle.Lifecycle

@Composable
fun rememberLifecycleState(atLeast: Lifecycle.State = Lifecycle.State.RESUMED): Boolean {
    var state by remember { mutableStateOf(Lifecycle.State.INITIALIZED) }
    val meet by remember { derivedStateOf { state >= atLeast } }

    val lifecycle = LocalRouteContext.current.lifecycle

    DisposableEffect(lifecycle) {
        val callbacks = object : Lifecycle.Callbacks {
            override fun onCreate() {
                state = Lifecycle.State.CREATED
            }

            override fun onDestroy() {
                state = Lifecycle.State.DESTROYED
            }

            override fun onPause() {
                state = Lifecycle.State.STARTED
            }

            override fun onResume() {
                state = Lifecycle.State.RESUMED
            }

            override fun onStart() {
                state = Lifecycle.State.STARTED
            }

            override fun onStop() {
                state = Lifecycle.State.CREATED
            }
        }

        lifecycle.subscribe(callbacks)

        onDispose {
            lifecycle.unsubscribe(callbacks)
        }
    }

    return meet
}

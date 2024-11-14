package kosh.presentation.di

import androidx.compose.runtime.Composable
import kosh.presentation.wc.RejectRequestState

interface PresentationComponent {

    fun reject(): Presenter<RejectRequestState>
}

fun interface Presenter<S : Any> {
    @Composable
    operator fun invoke(): S
}

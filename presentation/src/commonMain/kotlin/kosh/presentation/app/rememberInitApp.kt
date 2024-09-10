package kosh.presentation.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kosh.domain.repositories.AppStateRepo
import kosh.presentation.di.di

@Composable
fun rememberInitApp(
    appStateRepo: AppStateRepo = di { appRepositories.appStateRepo },
): InitAppState {
    var loaded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        appStateRepo.init()
        loaded = true
    }

    return InitAppState(
        loaded = loaded
    )
}

data class InitAppState(
    val loaded: Boolean,
)

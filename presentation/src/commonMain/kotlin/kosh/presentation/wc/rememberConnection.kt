package kosh.presentation.wc

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kosh.domain.usecases.wc.WcConnectionService
import kosh.domain.usecases.wc.useConnection
import kosh.presentation.di.di

@Composable
fun rememberConnection(
    connectionService: WcConnectionService = di { domain.wcConnectionService },
): ConnectionState {
    val connected by connectionService.connected.collectAsState(false)

    LaunchedEffect(Unit) {
        connectionService.useConnection()
    }

    return ConnectionState(
        connected = connected
    )
}

@Immutable
data class ConnectionState(
    val connected: Boolean,
)

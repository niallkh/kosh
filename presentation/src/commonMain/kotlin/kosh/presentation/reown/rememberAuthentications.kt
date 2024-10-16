package kosh.presentation.reown

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kosh.domain.models.reown.WcAuthentication
import kosh.domain.serializers.ImmutableList
import kosh.domain.usecases.reown.WcAuthenticationService
import kosh.presentation.core.di
import kosh.presentation.di.rememberLifecycleState
import kosh.presentation.di.rememberRetained
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList

@Composable
fun rememberAuthentications(
    authenticationService: WcAuthenticationService = di { domain.wcAuthenticationService },
): AuthenticationsState {
    var authentications by rememberRetained { mutableStateOf(persistentListOf<WcAuthentication>()) }

    if (rememberLifecycleState()) {
        LaunchedEffect(Unit) {
            authenticationService.authentications.collect {
                authentications = it.toPersistentList()
            }
        }
    }

    return AuthenticationsState(
        authentications = authentications.toImmutableList(),
    )
}

data class AuthenticationsState(
    val authentications: ImmutableList<WcAuthentication>,
)

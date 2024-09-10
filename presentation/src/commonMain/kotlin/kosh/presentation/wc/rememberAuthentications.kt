package kosh.presentation.wc

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kosh.domain.models.wc.WcAuthentication
import kosh.domain.serializers.ImmutableList
import kosh.domain.serializers.ImmutableListSerializer
import kosh.domain.usecases.wc.WcAuthenticationService
import kosh.presentation.di.di
import kosh.presentation.di.rememberLifecycleState
import kosh.presentation.di.rememberSerializable
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

@Composable
fun rememberAuthentications(
    authenticationService: WcAuthenticationService = di { domain.wcAuthenticationService },
): AuthenticationsState {
    var authentications by rememberSerializable(
        stateSerializer = ImmutableListSerializer(WcAuthentication.serializer())
    ) {
        mutableStateOf(persistentListOf())
    }

    if (rememberLifecycleState()) {
        LaunchedEffect(Unit) {
            authenticationService.authentications.collect {
                authentications = it.toPersistentList()
            }
        }
    }

    return AuthenticationsState(
        authentications = authentications,
    )
}

data class AuthenticationsState(
    val authentications: ImmutableList<WcAuthentication>,
)

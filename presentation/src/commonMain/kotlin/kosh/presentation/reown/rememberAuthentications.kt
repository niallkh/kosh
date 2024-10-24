package kosh.presentation.reown

import androidx.compose.runtime.Composable
import kosh.domain.models.reown.WcAuthentication
import kosh.domain.serializers.ImmutableList
import kosh.domain.usecases.reown.WcAuthenticationService
import kosh.presentation.core.di
import kosh.presentation.rememberCollect
import kotlinx.collections.immutable.persistentListOf

@Composable
fun rememberAuthentications(
    authenticationService: WcAuthenticationService = di { domain.wcAuthenticationService },
): AuthenticationsState {
    val authentications = rememberCollect(persistentListOf()) {
        authenticationService.authentications
    }

    return AuthenticationsState(
        authentications = authentications.result,
    )
}

data class AuthenticationsState(
    val authentications: ImmutableList<WcAuthentication>,
)

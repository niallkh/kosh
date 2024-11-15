package kosh.presentation.wc

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
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

    return remember {
        object : AuthenticationsState {
            override val authentications get() = authentications.result
            override fun retry() {
                authentications.retry()
            }
        }
    }
}

@Stable
interface AuthenticationsState {
    val authentications: ImmutableList<WcAuthentication>
    fun retry()
}

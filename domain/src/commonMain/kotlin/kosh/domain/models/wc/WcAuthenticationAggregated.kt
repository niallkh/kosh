package kosh.domain.models.wc

import androidx.compose.runtime.Immutable
import kosh.domain.entities.AccountEntity
import kosh.domain.entities.NetworkEntity
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class WcAuthenticationAggregated(
    val authentication: WcAuthentication,
    val accounts: List<AccountEntity>,
    val networks: List<NetworkEntity>,
)

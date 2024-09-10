package kosh.domain.models.token

import androidx.compose.runtime.Immutable
import kosh.domain.entities.AccountEntity
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class AccountBalance(
    val account: AccountEntity,
    val value: Balance,
)

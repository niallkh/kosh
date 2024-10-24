package kosh.domain.models.token

import androidx.compose.runtime.Immutable
import kosh.domain.entities.TokenEntity
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class TokenBalance(
    val token: TokenEntity,
    val value: Balance,
) 

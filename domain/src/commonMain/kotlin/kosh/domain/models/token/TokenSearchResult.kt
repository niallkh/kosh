package kosh.domain.models.token

import androidx.compose.runtime.Immutable
import kosh.domain.entities.NetworkEntity
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class TokenSearchResult(
    val value: TokenMetadata,
    val network: NetworkEntity,
)


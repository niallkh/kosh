package kosh.domain.models.token

import androidx.compose.runtime.Immutable
import kosh.domain.serializers.ImmutableList
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class NftExtendedMetadata(
    val description: String?,
    val attributes: ImmutableList<Attribute>,
) {

    @Serializable
    data class Attribute(
        val traitType: String?,
        val value: String?,
        val displayType: String?,
    )
}

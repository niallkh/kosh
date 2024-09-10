package kosh.domain.models.wc

import androidx.compose.runtime.Immutable
import kosh.domain.serializers.Uri
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class DappMetadata(
    val name: String,
    val description: String?,
    val url: Uri?,
    val icon: Uri?,
)

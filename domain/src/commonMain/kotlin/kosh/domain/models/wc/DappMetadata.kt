package kosh.domain.models.wc

import androidx.compose.runtime.Immutable
import kosh.domain.serializers.Uri
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
@Immutable
data class DappMetadata(
    val name: String,
    val url: Uri?,
    val icon: Uri?,
    @Transient
    val description: String? = null,
)

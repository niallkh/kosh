package kosh.domain.models.reown

import androidx.compose.runtime.Immutable
import kosh.domain.models.Uri
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class DappMetadata(
    val name: String,
    val url: Uri?,
    val icon: Uri?,
    val description: String? = null,
)

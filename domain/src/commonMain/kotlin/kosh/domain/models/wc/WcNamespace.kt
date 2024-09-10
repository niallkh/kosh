package kosh.domain.models.wc

import androidx.compose.runtime.Immutable
import arrow.optics.optics
import kosh.domain.models.ChainAddress
import kosh.domain.models.ChainId
import kotlinx.serialization.Serializable

@Serializable
@Immutable
@optics
data class WcNamespace(
    val requiredChains: List<ChainId>,
    val requiredEvents: List<String>,
    val requiredMethods: List<String>,

    val optionalChains: List<ChainId>,
    val optionalEvents: List<String>,
    val optionalMethods: List<String>,

    val approvedChainIds: List<ChainId>,
    val approvedAccounts: List<ChainAddress>,
    val approvedEvents: List<String>,
    val approvedMethods: List<String>,
) {
    companion object
}

package kosh.domain.models.token

import androidx.compose.runtime.Immutable
import kosh.domain.models.Address
import kosh.domain.models.ChainId
import kosh.domain.models.Uri
import kosh.domain.serializers.BigInteger
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class NftMetadata(
    val chainId: ChainId,
    val address: Address,
    val tokenId: BigInteger,
    val uri: Uri,
    val name: String?,
    val description: String?,
    val symbol: String?,
    val decimals: UByte?,
    val image: Uri,
    val external: Uri?,
) {

    companion object
}

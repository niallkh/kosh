package kosh.domain.models.token

import androidx.compose.runtime.Immutable
import arrow.optics.optics
import kosh.domain.entities.TokenEntity
import kosh.domain.models.Address
import kosh.domain.models.ChainId
import kosh.domain.serializers.Uri
import kotlinx.serialization.Serializable

@Serializable
@Immutable
@optics
data class TokenMetadata(
    val chainId: ChainId,
    val address: Address,
    val name: String,
    val symbol: String,
    val decimals: UByte,
    val icon: Uri?,
    val type: Type,
) {
    enum class Type {
        ERC20,
        ERC721,
        ERC1155,
    }

    companion object
}

fun TokenMetadata.Type.map() = when (this) {
    TokenMetadata.Type.ERC20 -> TokenEntity.Type.Erc20
    TokenMetadata.Type.ERC721 -> TokenEntity.Type.Erc721
    TokenMetadata.Type.ERC1155 -> TokenEntity.Type.Erc1155
}

val TokenMetadata.isNft: Boolean
    inline get() = when (type) {
        TokenMetadata.Type.ERC20,
        -> false

        TokenMetadata.Type.ERC1155,
        TokenMetadata.Type.ERC721,
        -> true
    }

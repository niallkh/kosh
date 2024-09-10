package kosh.domain.models.web3

import androidx.compose.runtime.Immutable
import arrow.optics.optics
import kosh.domain.entities.TokenEntity
import kosh.domain.models.Address
import kosh.domain.models.ByteString
import kosh.domain.models.ChainId
import kosh.domain.models.FunSelector
import kosh.domain.serializers.BigInteger
import kotlinx.serialization.Serializable

@Serializable
@Immutable
@optics
sealed interface ContractCall {

    val selector: FunSelector?
        get() = null

    @Serializable
    @Immutable
    @optics
    data class Transfer(
        val chainId: ChainId,
        val token: Address,
        override val selector: FunSelector,
        val sender: Address,
        val from: Address,
        val destination: Address,
        val tokenIds: List<BigInteger>,
        val amounts: List<BigInteger>,
        val data: ByteString,
    ) : ContractCall {
        companion object
    }

    @Serializable
    @Immutable
    @optics
    data class NativeTransfer(
        val token: TokenEntity,
        val destination: Address,
        val amount: BigInteger,
    ) : ContractCall {
        companion object
    }

    @Serializable
    @Immutable
    @optics
    data class Approve(
        val chainId: ChainId,
        val token: Address,
        override val selector: FunSelector,
        val owner: Address,
        val spender: Address,
        val tokenId: BigInteger?,
        val approved: BigInteger,
    ) : ContractCall {
        companion object
    }

    @Serializable
    @Immutable
    @optics
    data class Fallback(
        val token: TokenEntity,
        override val selector: FunSelector?,
        val from: Address,
        val contract: Address,
        val value: BigInteger,
        val input: ByteString,
    ) : ContractCall {
        companion object
    }

    @Serializable
    @Immutable
    @optics
    data class Deploy(
        val token: TokenEntity,
        val from: Address,
        val value: BigInteger,
        val input: ByteString,
    ) : ContractCall {
        companion object
    }

    companion object
}

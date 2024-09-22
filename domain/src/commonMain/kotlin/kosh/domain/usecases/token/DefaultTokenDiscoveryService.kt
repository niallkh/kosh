@file:OptIn(FlowPreview::class)

package kosh.domain.usecases.token

import arrow.core.None
import arrow.core.raise.catch
import arrow.core.raise.either
import arrow.core.raise.option
import arrow.core.raise.recover
import arrow.fx.coroutines.parMapUnordered
import co.touchlab.kermit.Logger
import com.ionspin.kotlin.bignum.integer.toBigInteger
import kosh.domain.entities.NetworkEntity
import kosh.domain.failure.Web3Failure
import kosh.domain.failure.logFailure
import kosh.domain.models.Address
import kosh.domain.models.ChainId
import kosh.domain.models.Uri
import kosh.domain.models.token.NftExtendedMetadata
import kosh.domain.models.token.NftMetadata
import kosh.domain.models.token.TokenMetadata
import kosh.domain.repositories.TokenListsRepo
import kosh.domain.repositories.TokenRepo
import kosh.domain.serializers.BigInteger
import kosh.domain.serializers.Either
import kosh.domain.state.AppState
import kosh.domain.state.AppStateProvider
import kosh.domain.state.activeNetworks
import kosh.domain.state.network
import kosh.domain.utils.optic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.withContext

class DefaultTokenDiscoveryService(
    private val tokenRepo: TokenRepo,
    private val tokenListsRepo: TokenListsRepo,
    private val appStateProvider: AppStateProvider,
) : TokenDiscoveryService {

    private val logger = Logger.withTag("[K]TokenDiscoveryService")

    override suspend fun searchToken(
        query: String,
    ): Either<Web3Failure, List<TokenMetadata>> = either {
        withContext(Dispatchers.Default) {
            Address(query).fold(
                ifLeft = { queryTokenLists(query) },
                ifRight = { address ->
                    listOfNotNull(queryTokenLists(address) ?: queryToken(address))
                },
            )
        }
    }

    override suspend fun getVerifiedTokens(): Flow<TokenMetadata> {
        return appStateProvider.optic(AppState.activeNetworks()).value.asFlow()
            .flatMapConcat { network -> tokenListsRepo.tokens(network.chainId) }
    }

    override suspend fun searchNft(
        token: TokenMetadata,
        query: String,
    ): Either<Web3Failure, NftMetadata?> = appStateProvider.optic(AppState.network(token.chainId))
        .mapLatest { network ->
            either {
                option {
                    val tokenId = parseTokenId(query)

                    queryNft(
                        chainId = network?.chainId ?: raise(None),
                        address = token.address,
                        tokenId = tokenId ?: raise(None),
                        type = token.type,
                    ).bind()
                }
            }
                .map { it.getOrNull() }
        }
        .first()

    private fun parseTokenId(query: String): BigInteger? = (catch({ query.toBigInteger() }) { null }
        ?: catch({ query.removePrefix("0x").toBigInteger(16) }) { null })

    override suspend fun getTokenMetadata(
        networkId: NetworkEntity.Id,
        address: Address,
    ): Either<Web3Failure, TokenMetadata?> = either {
        val network = appStateProvider.optic(AppState.network(networkId)).value
            ?: return@either null

        tokenRepo.getTokenMetadata(network.chainId, address).bind()
    }

    override suspend fun getNftMetadata(
        networkId: NetworkEntity.Id,
        address: Address,
        tokenId: BigInteger,
        type: TokenMetadata.Type,
        refresh: Boolean,
    ): Either<Web3Failure, NftMetadata?> = either {
        val network = appStateProvider.optic(AppState.network(networkId)).value
            ?: return@either null

        tokenRepo.getNftMetadata(network.chainId, address, tokenId, type, refresh).bind()
    }

    override suspend fun getNftExtendedMetadata(
        uri: Uri,
        refresh: Boolean,
    ): Either<Web3Failure, NftExtendedMetadata?> = either {
        tokenRepo.getNftMetadata(uri, refresh).bind()
    }

    private suspend fun queryTokenLists(
        q: String,
    ): List<TokenMetadata> = getVerifiedTokens()
        .mapNotNull { token ->
            when {
                token.symbol.startsWith(q, ignoreCase = true) -> 0 to token
                token.symbol.contains(q, ignoreCase = true) -> 1 to token
                token.name.startsWith(q, ignoreCase = true) -> 2 to token
                token.name.contains(q, ignoreCase = true) -> 3 to token
                else -> null
            }
        }
        .take(64)
        .toList()
        .sortedWith(compareBy({ (weight, _) -> weight }, { (_, token) -> token.symbol }))
        .map { (_, token) -> token }

    private suspend fun queryTokenLists(
        address: Address,
    ): TokenMetadata? = getVerifiedTokens()
        .firstOrNull { it.address == address }

    private suspend fun queryToken(
        address: Address,
    ): TokenMetadata? = appStateProvider.optic(AppState.activeNetworks()).value.asFlow()
        .parMapUnordered { network ->
            recover({
                tokenRepo.getTokenMetadata(network.chainId, address).bind()
            }) {
                logger.logFailure(it)
                null
            }
        }
        .filterNotNull()
        .firstOrNull()

    private suspend fun queryNft(
        chainId: ChainId,
        address: Address,
        tokenId: BigInteger,
        type: TokenMetadata.Type,
    ): Either<Web3Failure, NftMetadata?> = either {
        tokenRepo.getNftMetadata(chainId, address, tokenId, type).bind()
    }
}

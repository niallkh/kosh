package kosh.domain.usecases.token

import arrow.core.raise.either
import arrow.core.raise.ensure
import kosh.domain.entities.TokenEntity
import kosh.domain.entities.TokenEntity.Type
import kosh.domain.entities.decimals
import kosh.domain.entities.modifiedAt
import kosh.domain.entities.name
import kosh.domain.entities.nullableExternal
import kosh.domain.entities.nullableIcon
import kosh.domain.entities.nullableImage
import kosh.domain.entities.nullableSymbol
import kosh.domain.entities.nullableTokenName
import kosh.domain.entities.nullableUri
import kosh.domain.entities.type
import kosh.domain.failure.TokenFailure
import kosh.domain.models.Address
import kosh.domain.models.ChainId
import kosh.domain.models.Uri
import kosh.domain.repositories.AppStateRepo
import kosh.domain.serializers.BigInteger
import kosh.domain.serializers.Either
import kosh.domain.state.AppState
import kosh.domain.state.network
import kosh.domain.state.optionalToken
import kosh.domain.state.token
import kosh.domain.state.tokens
import kotlinx.datetime.Clock

class DefaultTokenService(
    private val appStateRepo: AppStateRepo,
) : TokenService {

    override suspend fun add(
        chainId: ChainId,
        address: Address?,
        tokenId: BigInteger?,
        name: String,
        symbol: String?,
        decimals: UByte,
        type: Type,
        tokenName: String?,
        icon: Uri?,
        uri: Uri?,
        external: Uri?,
        image: Uri?,
    ): Either<TokenFailure, TokenEntity.Id> = either {
        val network = AppState.network(chainId).get(appStateRepo.state)
            ?: raise(TokenFailure.NotFound())

        val token = TokenEntity(
            networkId = network.id,
            address = address,
            tokenId = tokenId,
            image = image,
            tokenName = tokenName,
            symbol = symbol,
            name = name,
            decimals = decimals,
            external = external,
            uri = uri,
            icon = icon,
            type = type,
        )

        appStateRepo.update {
            ensure(token.id !in AppState.tokens.get()) {
                TokenFailure.AlreadyExist()
            }

            AppState.token(token.id) set token
        }

        token.id
    }

    override suspend fun update(
        id: TokenEntity.Id,
        name: String,
        symbol: String?,
        decimals: UByte,
        type: Type,
        tokenName: String?,
        icon: Uri?,
        uri: Uri?,
        external: Uri?,
        image: Uri?,
    ): Either<TokenFailure, Unit> = either {
        appStateRepo.update {
            ensure(id in AppState.tokens.get()) {
                TokenFailure.NotFound()
            }

            inside(AppState.optionalToken(id)) {
                TokenEntity.name set name
                TokenEntity.nullableSymbol set symbol
                TokenEntity.decimals set decimals
                TokenEntity.type set type
                TokenEntity.nullableTokenName set tokenName
                TokenEntity.nullableIcon set icon
                TokenEntity.nullableUri set uri
                TokenEntity.nullableExternal set external
                TokenEntity.nullableImage set image
                TokenEntity.modifiedAt set Clock.System.now()
            }
        }
    }

    override suspend fun delete(id: TokenEntity.Id) {
        appStateRepo.update {
            AppState.token(id) set null
        }
    }
}

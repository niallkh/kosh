package kosh.domain.entities

import androidx.compose.runtime.Immutable
import arrow.core.memoize
import arrow.optics.optics
import kosh.domain.models.Address
import kosh.domain.models.Uri
import kosh.domain.models.eip55
import kosh.domain.serializers.BigInteger
import kosh.domain.serializers.Uuid
import kosh.domain.serializers.UuidSerializer
import kosh.domain.serializers.serializer
import kosh.domain.uuid.UuidNil
import kosh.domain.uuid.uuid5
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

private val namespace = uuid5(UuidNil, "TokenEntity")

@Serializable
@Immutable
@optics
data class TokenEntity(
    override val id: Id,
    val networkId: NetworkEntity.Id,
    val address: Address?,
    val tokenId: BigInteger?,
    val name: String,
    val symbol: String?,
    val decimals: UByte,
    val type: Type,
    val tokenName: String?,
    val icon: Uri?,
    val uri: Uri?,
    val external: Uri?,
    val image: Uri?,
    override val createdAt: Instant,
    override val modifiedAt: Instant,
) : Entity {

    @JvmInline
    @Immutable
    @Serializable(Id.Companion.Serializer::class)
    value class Id private constructor(override val value: Uuid) : Entity.Id<TokenEntity> {

        companion object {
            private val memo = ::Id.memoize()

            object Serializer :
                KSerializer<Id> by serializer(UuidSerializer, { it.value }, { memo(it) })

            operator fun invoke(
                networkId: NetworkEntity.Id,
                address: Address? = null,
                tokenId: BigInteger? = null,
            ) = memo(uuid5(namespace, buildString {
                append(networkId.value.toString())
                if (address != null) {
                    append(":")
                    append(address.eip55())
                }
                if (tokenId != null) {
                    check(address != null)
                    append(":")
                    append(tokenId.toString(10))
                }
            }))
        }
    }

    enum class Type {
        Native,
        Erc20,
        Erc721,
        Erc1155,
    }

    companion object {
        operator fun invoke(
            networkId: NetworkEntity.Id,
            address: Address? = null,
            tokenId: BigInteger? = null,
            name: String,
            symbol: String?,
            decimals: UByte,
            type: Type,
            tokenName: String? = null,
            icon: Uri? = null,
            uri: Uri? = null,
            external: Uri? = null,
            image: Uri? = null,
        ) = TokenEntity(
            id = Id(networkId, address, tokenId),
            networkId = networkId,
            address = address,
            tokenId = tokenId,
            name = name,
            symbol = symbol,
            decimals = decimals,
            type = type,
            tokenName = tokenName,
            icon = icon,
            uri = uri,
            external = external,
            image = image,
            createdAt = Clock.System.now(),
            modifiedAt = Clock.System.now(),
        )
    }
}

val TokenEntity.isNft: Boolean
    inline get() = when (type) {
        TokenEntity.Type.Native,
        TokenEntity.Type.Erc20,
        -> false

        TokenEntity.Type.Erc1155,
        TokenEntity.Type.Erc721,
        -> true
    }


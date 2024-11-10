package kosh.domain.entities

import androidx.compose.runtime.Immutable
import arrow.core.memoize
import arrow.optics.optics
import kosh.domain.models.ChainId
import kosh.domain.models.Uri
import kosh.domain.serializers.Uuid
import kosh.domain.serializers.UuidSerializer
import kosh.domain.serializers.serializer
import kosh.domain.uuid.UuidNil
import kosh.domain.uuid.uuid5
import kotlinx.collections.immutable.persistentListOf
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

private val namespace = uuid5(UuidNil, "NetworkEntity")

@Serializable
@Immutable
@optics
data class NetworkEntity(
    val id: Id,
    val chainId: ChainId,
    val name: String,
    val readRpcProvider: Uri,
    val writeRpcProvider: Uri?,
    val explorers: List<Uri>,
    val icon: Uri?,
    val testnet: Boolean,
    val createdAt: Instant,
    val modifiedAt: Instant,
) {

    @JvmInline
    @Immutable
    @Serializable(Id.Companion.Serializer::class)
    value class Id private constructor(val value: Uuid) {

        companion object {
            private val memo = ::Id.memoize()

            object Serializer :
                KSerializer<Id> by serializer(UuidSerializer, { it.value }, { memo(it) })

            operator fun invoke(chainId: ChainId) = memo(uuid5(namespace, chainId.value.toString()))
        }
    }

    companion object {
        operator fun invoke(
            chainId: ChainId,
            name: String,
            readRpcProvider: Uri,
            writeRpcProvider: Uri? = null,
            testnet: Boolean = false,
            explorers: List<Uri> = persistentListOf(),
            icon: Uri? = null,
        ) = NetworkEntity(
            id = Id(chainId),
            chainId = chainId,
            name = name,
            readRpcProvider = readRpcProvider,
            writeRpcProvider = writeRpcProvider,
            explorers = explorers,
            icon = icon,
            createdAt = Clock.System.now(),
            modifiedAt = Clock.System.now(),
            testnet = testnet
        )
    }
}

val ChainId.networkId: NetworkEntity.Id
    inline get() = NetworkEntity.Id(this)

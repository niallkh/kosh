package kosh.domain.entities

import androidx.compose.runtime.Immutable
import arrow.core.memoize
import arrow.optics.optics
import kosh.domain.models.Address
import kosh.domain.models.eip55
import kosh.domain.serializers.Uuid
import kosh.domain.serializers.UuidSerializer
import kosh.domain.serializers.serializer
import kosh.domain.uuid.UuidNil
import kosh.domain.uuid.uuid5
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

private val namespace = uuid5(UuidNil, "WalletEntity")

@Serializable
@Immutable
@optics
data class WalletEntity(
    val id: Id,
    val name: String,
    val location: Location,
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

            private fun id(mainAddress: Address) = memo(uuid5(namespace, mainAddress.eip55()))

            operator fun invoke(location: Location) = id(location.mainAddress)

            operator fun invoke(mainAddress: Address) = id(mainAddress)
        }
    }

    @Serializable
    @Immutable
    @optics
    sealed interface Location {
        val mainAddress: Address

        @Serializable
        @SerialName("trezor")
        @Immutable
        @optics
        data class Trezor(
            override val mainAddress: Address,
            val product: String?,
            val model: String?,
            val name: String?,
            val color: Int?,
        ) : Location {

            companion object
        }

        @Serializable
        @SerialName("ledger")
        @Immutable
        @optics
        data class Ledger(
            override val mainAddress: Address,
            val product: String?,
        ) : Location {

            companion object
        }

        companion object
    }

    @Serializable
    enum class Type {
        Trezor,
        Ledger,
    }

    companion object {


        operator fun invoke(
            name: String,
            location: Location,
        ): WalletEntity = WalletEntity(
            id = Id(location),
            name = name,
            location = location,
            createdAt = Clock.System.now(),
            modifiedAt = Clock.System.now(),
        )
    }
}

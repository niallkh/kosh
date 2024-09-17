package kosh.domain.entities

import androidx.compose.runtime.Immutable
import arrow.core.memoize
import arrow.optics.optics
import kosh.domain.models.Address
import kosh.domain.models.account.DerivationPath
import kosh.domain.models.eip55
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

private val namespace = uuid5(UuidNil, "AccountEntity")

@Serializable
@Immutable
@optics
data class AccountEntity(
    override val id: Id,
    val walletId: WalletEntity.Id,
    val name: String,
    val derivationPath: DerivationPath,
    val address: Address,
    override val createdAt: Instant,
    override val modifiedAt: Instant,
) : Entity {

    @JvmInline
    @Immutable
    @Serializable(Id.Companion.Serializer::class)
    value class Id private constructor(override val value: Uuid) : Entity.Id<AccountEntity> {

        companion object {
            private val memo = ::Id.memoize()

            object Serializer :
                KSerializer<Id> by serializer(UuidSerializer, { it.value }, { memo(it) })

            private fun id(address: Address) = memo(uuid5(namespace, address.eip55()))

            operator fun invoke(address: Address): Id = id(address)
        }
    }

    companion object {
        operator fun invoke(
            address: Address,
            walletId: WalletEntity.Id,
            derivationPath: DerivationPath,
            name: String,
        ): AccountEntity = AccountEntity(
            id = Id(address),
            walletId = walletId,
            address = address,
            derivationPath = derivationPath,
            name = name,
            createdAt = Clock.System.now(),
            modifiedAt = Clock.System.now()
        )
    }
}

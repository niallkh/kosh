package kosh.domain.entities

import androidx.compose.runtime.Immutable
import arrow.optics.optics
import kosh.domain.models.Address
import kosh.domain.models.Hash
import kosh.domain.models.Uri
import kosh.domain.models.web3.GasPrice
import kosh.domain.models.web3.Receipt
import kosh.domain.serializers.BigInteger
import kosh.domain.serializers.Path
import kosh.domain.serializers.Uuid
import kosh.domain.uuid.UuidNil
import kosh.domain.uuid.uuid5
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

private val namespace = uuid5(UuidNil, "TokenEntity")

@Serializable
@Immutable
@optics
sealed interface TransactionEntity : Entity {
    override val id: Id
    val dapp: Dapp

    @Serializable
    @Immutable
    @optics
    @SerialName("eip1559")
    data class Eip1559(
        override val id: Id,
        val networkId: NetworkEntity.Id,
        val sender: AccountEntity.Id,
        val target: Address?,
        val value: BigInteger,
        val data: Path,
        override val dapp: Dapp,
        val nonce: ULong,
        val gasLimit: ULong,
        val gasPrice: GasPrice,
        override val createdAt: Instant,
        override val modifiedAt: Instant,

        val hash: Hash,
        val receipt: Receipt?,
        val logs: Path?,
    ) : TransactionEntity {

        companion object {
            operator fun invoke(
                networkId: NetworkEntity.Id,
                sender: AccountEntity.Id,
                target: Address?,
                value: BigInteger,
                data: Path,
                dapp: Dapp,
                nonce: ULong,
                gasLimit: ULong,
                hash: Hash,
                gasPrice: GasPrice,
            ) = Eip1559(
                id = Id(networkId, sender, nonce),
                data = data,
                nonce = nonce,
                gasLimit = gasLimit,
                gasPrice = gasPrice,
                networkId = networkId,
                target = target,
                sender = sender,
                value = value,
                dapp = dapp,
                createdAt = Clock.System.now(),
                modifiedAt = Clock.System.now(),
                hash = hash,
                receipt = null,
                logs = null,
            )
        }
    }

    @Serializable
    @Immutable
    @optics
    @SerialName("pmsg")
    data class PersonalMessage(
        override val id: Id,
        val sender: AccountEntity.Id,
        override val dapp: Dapp,
        val message: Path,
        override val createdAt: Instant,
        override val modifiedAt: Instant,
    ) : TransactionEntity {

        companion object {
            operator fun invoke(
                sender: AccountEntity.Id,
                dapp: Dapp,
                message: Path,
            ) = PersonalMessage(
                id = Id(
                    sender = sender,
                    message = message,
                ),
                sender = sender,
                dapp = dapp,
                createdAt = Clock.System.now(),
                modifiedAt = Clock.System.now(),
                message = message,
            )
        }
    }

    @Serializable
    @Immutable
    @optics
    @SerialName("eip712")
    data class Eip712(
        override val id: Id,
        val networkId: NetworkEntity.Id?,
        val sender: AccountEntity.Id,
        override val dapp: Dapp,
        val jsonTypeData: Path,
        override val createdAt: Instant,
        override val modifiedAt: Instant,
    ) : TransactionEntity {

        companion object {
            operator fun invoke(
                sender: AccountEntity.Id,
                dapp: Dapp,
                networkId: NetworkEntity.Id?,
                jsonTypeData: Path,
            ) = Eip712(
                id = Id(
                    sender = sender,
                    networkId = networkId,
                ),
                sender = sender,
                dapp = dapp,
                networkId = networkId,
                jsonTypeData = jsonTypeData,
                createdAt = Clock.System.now(),
                modifiedAt = Clock.System.now(),
            )
        }
    }

    @JvmInline
    @Serializable
    @Immutable
    value class Id private constructor(override val value: Uuid) : Entity.Id<TransactionEntity> {

        companion object {
            operator fun invoke(
                network: NetworkEntity.Id,
                sender: AccountEntity.Id,
                nonce: ULong,
            ) = Id(
                uuid5(
                    namespace,
                    listOfNotNull(
                        network.value.toString(),
                        sender.value.toString(),
                        nonce.toString(),
                    ).joinToString(separator = ":")
                )
            )

            operator fun invoke(
                networkId: NetworkEntity.Id?,
                sender: AccountEntity.Id,
            ) = Id(
                uuid5(
                    namespace,
                    listOfNotNull(
                        networkId?.value?.toString(),
                        sender.value.toString(),
                    ).joinToString(separator = ":")
                )
            )

            operator fun invoke(
                sender: AccountEntity.Id,
                message: Path,
            ) = Id(
                uuid5(
                    namespace,
                    listOfNotNull(
                        sender.value.toString(),
                        message.toString(),
                    ).joinToString(separator = ":")
                )
            )
        }
    }

    @Serializable
    @Immutable
    data class Dapp(
        val name: String,
        val url: Uri?,
        val icon: Uri?,
    )

    companion object
}

val TransactionEntity.networkId: NetworkEntity.Id?
    inline get() = TransactionEntity.eip1559.networkId.getOrNull(this)
        ?: TransactionEntity.eip712.networkId.getOrNull(this)

val TransactionEntity.accountId: AccountEntity.Id?
    inline get() = TransactionEntity.eip1559.sender.getOrNull(this)
        ?: TransactionEntity.eip712.sender.getOrNull(this)
        ?: TransactionEntity.personalMessage.sender.getOrNull(this)

val TransactionEntity.path: Path?
    inline get() = TransactionEntity.eip1559.data.getOrNull(this)
        ?: TransactionEntity.eip712.jsonTypeData.getOrNull(this)
        ?: TransactionEntity.personalMessage.message.getOrNull(this)

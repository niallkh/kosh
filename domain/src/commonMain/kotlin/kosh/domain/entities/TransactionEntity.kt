package kosh.domain.entities

import androidx.compose.runtime.Immutable
import arrow.optics.optics
import kosh.domain.models.Address
import kosh.domain.models.ByteString
import kosh.domain.models.Hash
import kosh.domain.models.Uri
import kosh.domain.models.web3.EthMessage
import kosh.domain.models.web3.GasPrice
import kosh.domain.models.web3.JsonTypeData
import kosh.domain.models.web3.Log
import kosh.domain.models.web3.Receipt
import kosh.domain.serializers.BigInteger
import kosh.domain.serializers.Uuid
import kosh.domain.uuid.UuidNil
import kosh.domain.uuid.uuid4
import kosh.domain.uuid.uuid5
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

val transactionsNamespace = uuid5(UuidNil, "TokenEntity")

@Serializable
@Immutable
@optics
sealed interface TransactionEntity {
    val id: Id
    val dapp: Dapp
    val createdAt: Instant

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

        val data: Reference<ByteString>,
        override val dapp: Dapp,
        val nonce: ULong,
        val gasLimit: ULong,
        val gasPrice: GasPrice,
        override val createdAt: Instant,
        val modifiedAt: Instant,

        val hash: Hash,
        val receipt: Receipt?,
        val logs: Reference<List<Log>>?,
    ) : TransactionEntity {

        companion object {
            operator fun invoke(
                networkId: NetworkEntity.Id,
                sender: AccountEntity.Id,
                target: Address?,
                value: BigInteger,
                data: Reference<ByteString>,
                dapp: Dapp,
                nonce: ULong,
                gasLimit: ULong,
                hash: Hash,
                gasPrice: GasPrice,
            ) = Eip1559(
                id = Id(),
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
        val message: Reference<EthMessage>,
        override val createdAt: Instant,
        val modifiedAt: Instant,
    ) : TransactionEntity {

        companion object {
            operator fun invoke(
                sender: AccountEntity.Id,
                dapp: Dapp,
                message: Reference<EthMessage>,
            ) = PersonalMessage(
                id = Id(),
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
        val jsonTypeData: Reference<JsonTypeData>,
        override val createdAt: Instant,
        val modifiedAt: Instant,
    ) : TransactionEntity {

        companion object {
            operator fun invoke(
                sender: AccountEntity.Id,
                dapp: Dapp,
                networkId: NetworkEntity.Id?,
                jsonTypeData: Reference<JsonTypeData>,
            ) = Eip712(
                id = Id(),
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
    value class Id private constructor(val value: Uuid) {

        companion object {
            operator fun invoke() = Id(
                uuid5(
                    transactionsNamespace,
                    uuid4().toString()
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

val TransactionEntity.path: Reference<*>?
    inline get() = TransactionEntity.eip1559.data.getOrNull(this)
        ?: TransactionEntity.eip712.jsonTypeData.getOrNull(this)
        ?: TransactionEntity.personalMessage.message.getOrNull(this)

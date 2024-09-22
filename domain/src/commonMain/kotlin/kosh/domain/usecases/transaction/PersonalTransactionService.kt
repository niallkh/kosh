package kosh.domain.usecases.transaction

import arrow.core.Ior
import arrow.core.raise.IorRaise
import arrow.core.raise.iorNel
import arrow.optics.dsl.at
import arrow.optics.typeclasses.At
import kosh.domain.entities.AccountEntity
import kosh.domain.entities.TransactionEntity
import kosh.domain.failure.TransactionFailure
import kosh.domain.models.ByteString
import kosh.domain.models.wc.DappMetadata
import kosh.domain.models.web3.EthMessage
import kosh.domain.models.web3.Signature
import kosh.domain.repositories.AppStateRepo
import kosh.domain.repositories.FilesRepo
import kosh.domain.repositories.modify
import kosh.domain.serializers.Nel
import kosh.domain.state.AppState
import kosh.domain.state.transactions
import kosh.domain.utils.pmap
import kotlinx.io.bytestring.encodeToByteString

class PersonalTransactionService(
    private val appStateRepo: AppStateRepo,
    private val fileRepo: FilesRepo,
) {

    suspend fun add(
        message: EthMessage,
        signature: Signature,
        dapp: DappMetadata,
    ): Ior<Nel<TransactionFailure>, Signature> = iorNel {

        val personalMessage = TransactionEntity.PersonalMessage(
            sender = AccountEntity.Id(signature.signer),
            dapp = TransactionEntity.Dapp(
                name = dapp.name,
                url = dapp.url,
                icon = dapp.icon
            ),
            message = fileRepo.write(ByteString(message.value.encodeToByteString())),
        )

        appStateRepo.modify {
            ensureAccumulating(personalMessage.id !in AppState.transactions.get()) {
                TransactionFailure.AlreadyExist()
            }

            if (personalMessage.id !in AppState.transactions.get()) {
                AppState.transactions.at(At.pmap(), personalMessage.id) set personalMessage
            }
        }

        signature
    }
}

inline fun <Error> IorRaise<Nel<Error>>.ensureAccumulating(condition: Boolean, raise: () -> Error) {
    return if (condition) Unit else accumulate(raise)
}

inline fun <Error> IorRaise<Nel<Error>>.accumulate(raise: () -> Error) {
    return Ior.bothNel(raise(), Unit).bind()
}

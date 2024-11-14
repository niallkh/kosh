package kosh.domain.usecases.transaction

import arrow.core.Ior
import arrow.core.raise.iorNel
import arrow.optics.dsl.at
import arrow.optics.typeclasses.At
import arrow.resilience.saga
import arrow.resilience.transact
import kosh.domain.entities.AccountEntity
import kosh.domain.entities.TransactionEntity
import kosh.domain.failure.TransactionFailure
import kosh.domain.models.reown.DappMetadata
import kosh.domain.models.web3.EthMessage
import kosh.domain.models.web3.Signature
import kosh.domain.repositories.AppStateRepo
import kosh.domain.repositories.ReferenceRepo
import kosh.domain.repositories.save
import kosh.domain.serializers.Nel
import kosh.domain.state.AppState
import kosh.domain.state.transactions
import kosh.domain.utils.ensureAccumulating
import kosh.domain.utils.pmap

class PersonalMessageService(
    private val appStateRepo: AppStateRepo,
    private val referenceRepo: ReferenceRepo,
) {

    suspend fun add(
        message: EthMessage,
        signature: Signature,
        dapp: DappMetadata,
    ): Ior<Nel<TransactionFailure>, Signature> = iorNel {
        saga {
            val messageRef = save(referenceRepo, message, EthMessage.serializer())

            val personalMessage = TransactionEntity.PersonalMessage(
                sender = AccountEntity.Id(signature.signer),
                dapp = TransactionEntity.Dapp(
                    name = dapp.name,
                    url = dapp.url,
                    icon = dapp.icon
                ),
                message = messageRef,
            )

            appStateRepo.update {
                ensureAccumulating(personalMessage.id !in AppState.transactions.get()) {
                    TransactionFailure.AlreadyExist()
                }

                if (personalMessage.id !in AppState.transactions.get()) {
                    AppState.transactions.at(At.pmap(), personalMessage.id) set personalMessage
                }
            }

            signature
        }.transact()
    }
}

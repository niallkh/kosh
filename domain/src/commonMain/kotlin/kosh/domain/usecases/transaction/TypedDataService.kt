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
import kosh.domain.models.ChainId
import kosh.domain.models.reown.DappMetadata
import kosh.domain.models.web3.JsonTypedData
import kosh.domain.models.web3.Signature
import kosh.domain.repositories.AppStateRepo
import kosh.domain.repositories.ReferenceRepo
import kosh.domain.repositories.save
import kosh.domain.serializers.Nel
import kosh.domain.state.AppState
import kosh.domain.state.network
import kosh.domain.state.transactions
import kosh.domain.utils.ensureAccumulating
import kosh.domain.utils.pmap

class TypedDataService(
    private val appStateRepo: AppStateRepo,
    private val referenceRepo: ReferenceRepo,
) {

    suspend fun add(
        jsonTypeData: JsonTypedData,
        signature: Signature,
        chainId: ChainId?,
        dapp: DappMetadata,
    ): Ior<Nel<TransactionFailure>, Signature> = iorNel {
        saga {
            val jsonTypeDataRef = save(referenceRepo, jsonTypeData, JsonTypedData.serializer())

            val eip712 = TransactionEntity.Eip712(
                sender = AccountEntity.Id(signature.signer),
                jsonTypeData = jsonTypeDataRef,
                dapp = TransactionEntity.Dapp(
                    name = dapp.name,
                    url = dapp.url,
                    icon = dapp.icon
                ),
                networkId = chainId?.let { AppState.network(it).get(appStateRepo.state)?.id }
            )

            appStateRepo.update {
                ensureAccumulating(eip712.id !in AppState.transactions.get()) {
                    TransactionFailure.AlreadyExist()
                }

                if (eip712.id !in AppState.transactions.get()) {
                    AppState.transactions.at(At.pmap(), eip712.id) set eip712
                }
            }

            signature

        }.transact()
    }
}

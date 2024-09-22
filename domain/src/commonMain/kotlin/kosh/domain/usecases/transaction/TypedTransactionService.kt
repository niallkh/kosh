package kosh.domain.usecases.transaction

import arrow.core.Ior
import arrow.core.raise.iorNel
import arrow.optics.dsl.at
import arrow.optics.typeclasses.At
import kosh.domain.entities.AccountEntity
import kosh.domain.entities.TransactionEntity
import kosh.domain.failure.TransactionFailure
import kosh.domain.models.ByteString
import kosh.domain.models.ChainId
import kosh.domain.models.wc.DappMetadata
import kosh.domain.models.web3.JsonTypeData
import kosh.domain.models.web3.Signature
import kosh.domain.repositories.AppStateRepo
import kosh.domain.repositories.FilesRepo
import kosh.domain.repositories.modify
import kosh.domain.repositories.optic
import kosh.domain.serializers.Nel
import kosh.domain.state.AppState
import kosh.domain.state.network
import kosh.domain.state.transactions
import kosh.domain.utils.pmap
import kotlinx.io.bytestring.encodeToByteString

class TypedTransactionService(
    private val fileRepo: FilesRepo,
    private val appStateRepo: AppStateRepo,
) {

    suspend fun add(
        jsonTypeData: JsonTypeData,
        signature: Signature,
        chainId: ChainId?,
        dapp: DappMetadata,
    ): Ior<Nel<TransactionFailure>, Signature> = iorNel {
        val jsonPath = fileRepo.write(ByteString(jsonTypeData.json.encodeToByteString()))

        val eip712 = TransactionEntity.Eip712(
            sender = AccountEntity.Id(signature.signer),
            jsonTypeData = jsonPath,
            dapp = TransactionEntity.Dapp(
                name = dapp.name,
                url = dapp.url,
                icon = dapp.icon
            ),
            networkId = chainId?.let { appStateRepo.optic(AppState.network(it)).value?.id }
        )

        appStateRepo.modify {
            ensureAccumulating(eip712.id !in AppState.transactions.get()) {
                TransactionFailure.AlreadyExist()
            }

            if (eip712.id !in AppState.transactions.get()) {
                AppState.transactions.at(At.pmap(), eip712.id) set eip712
            }
        }

        signature
    }
}

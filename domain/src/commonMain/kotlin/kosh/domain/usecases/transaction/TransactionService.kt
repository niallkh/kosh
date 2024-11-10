package kosh.domain.usecases.transaction

import kosh.domain.entities.TransactionEntity
import kosh.domain.entities.path
import kosh.domain.repositories.AppStateRepo
import kosh.domain.repositories.ReferenceRepo
import kosh.domain.state.AppState
import kosh.domain.state.transaction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TransactionService(
    private val appStateRepo: AppStateRepo,
    private val applicationScope: CoroutineScope,
    private val referenceRepo: ReferenceRepo,
) {

    suspend fun delete(
        id: TransactionEntity.Id,
    ) {
        withContext(NonCancellable) {
            val transaction = AppState.transaction(id).get(appStateRepo.state)

            appStateRepo.update {
                AppState.transaction(id) set null
            }

            transaction?.path?.let {
                applicationScope.launch {
                    referenceRepo.remove(it)
                }
            }
        }
    }
}

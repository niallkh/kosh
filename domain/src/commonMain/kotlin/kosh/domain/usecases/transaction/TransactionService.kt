package kosh.domain.usecases.transaction

import kosh.domain.entities.TransactionEntity
import kosh.domain.entities.path
import kosh.domain.repositories.AppStateRepo
import kosh.domain.repositories.FilesRepo
import kosh.domain.repositories.modify
import kosh.domain.repositories.optic
import kosh.domain.state.AppState
import kosh.domain.state.transaction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class TransactionService(
    private val appStateRepo: AppStateRepo,
    private val filesRepo: FilesRepo,
    private val applicationScope: CoroutineScope,
) {

    fun delete(
        id: TransactionEntity.Id,
    ) {
        val transaction = appStateRepo.optic(AppState.transaction(id)).value

        appStateRepo.modify {
            AppState.transaction(id) set null
        }

        transaction?.path?.let {
            applicationScope.launch {
                filesRepo.delete(it)
            }
        }
    }
}

package kosh.ui.navigation.routes

import kosh.domain.entities.TransactionEntity
import kotlinx.serialization.Serializable

@Serializable
sealed class TransactionsRoute : Route {

    @Serializable
    data class Details(val id: TransactionEntity.Id) : TransactionsRoute()

    @Serializable
    data class Delete(val id: TransactionEntity.Id) : TransactionsRoute()
}

package kosh.domain.failure

import androidx.compose.runtime.Immutable

@Immutable
sealed interface TransactionFailure : AppFailure {

    class AlreadyExist : TransactionFailure {
        override val message: String
            get() = "Transaction is already exist"
    }

    class NotFound : TransactionFailure {
        override val message: String
            get() = "Transaction not found"
    }

    class ReceiptNotAvailable : TransactionFailure {
        override val message: String
            get() = "Receipt not available"
    }

    class InvalidTransaction : TransactionFailure {
        override val message: String
            get() = "Invalid transaction"
    }

    class Underpriced : TransactionFailure {
        override val message: String
            get() = "Underpriced"
    }

    class Connection(val failure: Web3Failure) : TransactionFailure {
        override val message: String
            get() = failure.message
    }
}

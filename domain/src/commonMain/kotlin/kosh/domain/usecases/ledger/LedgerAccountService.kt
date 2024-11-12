package kosh.domain.usecases.ledger

import arrow.core.Either
import kosh.domain.failure.LedgerFailure
import kosh.domain.models.Address
import kosh.domain.models.hw.Ledger
import kosh.domain.models.web3.EthMessage
import kosh.domain.models.web3.JsonTypedData
import kosh.domain.models.web3.Signature
import kosh.domain.models.web3.Signer
import kosh.domain.models.web3.TransactionData
import kosh.domain.repositories.LedgerListener
import kotlinx.coroutines.flow.Flow

interface LedgerAccountService {

    fun getAccounts(
        listener: LedgerListener,
        ledger: Ledger,
        fromIndex: UInt,
        amount: UInt,
    ): Flow<Either<LedgerFailure, Signer>>

    suspend fun sign(
        listener: LedgerListener,
        ledger: Ledger,
        address: Address,
        jsonTypeData: JsonTypedData,
    ): Either<LedgerFailure, Signature>

    suspend fun sign(
        listener: LedgerListener,
        ledger: Ledger,
        address: Address,
        message: EthMessage,
    ): Either<LedgerFailure, Signature>

    suspend fun sign(
        listener: LedgerListener,
        ledger: Ledger,
        transaction: TransactionData,
    ): Either<LedgerFailure, Signature>
}

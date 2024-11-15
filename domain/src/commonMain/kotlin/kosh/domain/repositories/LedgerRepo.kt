package kosh.domain.repositories

import arrow.core.Either
import kosh.domain.failure.LedgerFailure
import kosh.domain.models.account.DerivationPath
import kosh.domain.models.hw.Ledger
import kosh.domain.models.web3.Signature
import kosh.domain.models.web3.Signer
import kosh.domain.models.web3.TransactionData
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

interface LedgerRepo : Repository {

    val list: Flow<ImmutableList<Ledger>>

    fun accounts(
        listener: LedgerListener,
        ledger: Ledger,
        paths: List<DerivationPath>,
    ): Flow<Either<LedgerFailure, Signer>>

    suspend fun signPersonalMessage(
        listener: LedgerListener,
        ledger: Ledger,
        message: String,
        derivationPath: DerivationPath,
    ): Either<LedgerFailure, Signature>

    suspend fun signTypedData(
        listener: LedgerListener,
        ledger: Ledger,
        typedMessage: String,
        derivationPath: DerivationPath,
    ): Either<LedgerFailure, Signature>

    suspend fun signTransaction(
        listener: LedgerListener,
        ledger: Ledger,
        transaction: TransactionData,
        derivationPath: DerivationPath,
    ): Either<LedgerFailure, Signature>
}

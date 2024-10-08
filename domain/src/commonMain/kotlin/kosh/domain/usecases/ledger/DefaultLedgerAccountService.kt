package kosh.domain.usecases.ledger

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import kosh.domain.failure.LedgerFailure
import kosh.domain.models.Address
import kosh.domain.models.account.ledgerDerivationPath
import kosh.domain.models.hw.Ledger
import kosh.domain.models.web3.EthMessage
import kosh.domain.models.web3.JsonTypeData
import kosh.domain.models.web3.Signature
import kosh.domain.models.web3.TransactionData
import kosh.domain.repositories.AppStateRepo
import kosh.domain.repositories.LedgerListener
import kosh.domain.repositories.LedgerRepo
import kosh.domain.repositories.optic
import kosh.domain.state.AppState
import kosh.domain.state.account
import kotlinx.coroutines.flow.firstOrNull

class DefaultLedgerAccountService(
    private val ledgerRepo: LedgerRepo,
    private val appStateRepo: AppStateRepo,
) : LedgerAccountService {

    override fun getAccounts(
        listener: LedgerListener,
        ledger: Ledger,
        fromIndex: UInt,
        amount: UInt,
    ) = ledgerRepo.accounts(
        listener = listener,
        ledger = ledger,
        paths = (fromIndex..<(fromIndex + amount)).map { ledgerDerivationPath(it) },
    )

    override suspend fun sign(
        listener: LedgerListener,
        ledger: Ledger,
        address: Address,
        message: EthMessage,
    ): Either<LedgerFailure, Signature> = either {
        val account = appStateRepo.optic(AppState.account(address)).firstOrNull()
            ?: raise(LedgerFailure.Other())

        val signature = ledgerRepo.signPersonalMessage(
            listener, ledger, message.value, account.derivationPath
        ).bind()

        ensure(signature.signer == address) {
            LedgerFailure.InvalidState()
        }

        signature
    }

    override suspend fun sign(
        listener: LedgerListener,
        ledger: Ledger,
        address: Address,
        jsonTypeData: JsonTypeData,
    ): Either<LedgerFailure, Signature> = either {
        val account = appStateRepo.optic(AppState.account(address)).firstOrNull()
            ?: raise(LedgerFailure.Other())

        val signature = ledgerRepo.signTypedMessage(
            listener, ledger, jsonTypeData.json, account.derivationPath
        ).bind()

        ensure(signature.signer == address) {
            LedgerFailure.InvalidState()
        }

        signature
    }

    override suspend fun sign(
        listener: LedgerListener,
        ledger: Ledger,
        transaction: TransactionData,
    ): Either<LedgerFailure, Signature> = either {

        val account = appStateRepo.optic(AppState.account(transaction.tx.from)).firstOrNull()
            ?: raise(LedgerFailure.Other())

        val signature = ledgerRepo.signTransaction(
            listener = listener,
            ledger = ledger,
            transaction = transaction,
            derivationPath = account.derivationPath,
        ).bind()

        ensure(signature.signer == transaction.tx.from) {
            LedgerFailure.InvalidState()
        }

        signature
    }
}

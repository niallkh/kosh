package kosh.domain.usecases.trezor

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import co.touchlab.kermit.Logger
import kosh.domain.failure.TrezorFailure
import kosh.domain.models.Address
import kosh.domain.models.account.ethereumDerivationPath
import kosh.domain.models.hw.Trezor
import kosh.domain.models.web3.EthMessage
import kosh.domain.models.web3.JsonTypeData
import kosh.domain.models.web3.Signature
import kosh.domain.models.web3.Signer
import kosh.domain.models.web3.TransactionData
import kosh.domain.repositories.AppStateRepo
import kosh.domain.repositories.TrezorListener
import kosh.domain.repositories.TrezorRepo
import kosh.domain.repositories.optic
import kosh.domain.state.AppState
import kosh.domain.state.account
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class DefaultTrezorAccountService(
    private val trezorRepo: TrezorRepo,
    private val appStateRepo: AppStateRepo,
) : TrezorAccountService {

    private val logger = Logger.withTag("[K]TrezorAccountService")

    override fun getAccounts(
        listener: TrezorListener,
        trezor: Trezor,
        refresh: Boolean,
        fromIndex: UInt,
        amount: UInt,
    ): Flow<Either<TrezorFailure, Signer>> = trezorRepo.accounts(
        listener = listener,
        trezor = trezor,
        refresh = refresh,
        paths = (fromIndex..<(fromIndex + amount))
            .map { ethereumDerivationPath(it) },
    )

    override suspend fun sign(
        listener: TrezorListener,
        trezor: Trezor,
        refresh: Boolean,
        address: Address,
        jsonTypeData: JsonTypeData,
    ): Either<TrezorFailure, Signature> = either {

        val account = appStateRepo.optic(AppState.account(address)).firstOrNull()
            ?: raise(TrezorFailure.Other())

        val signature = trezorRepo.signTypedMessage(
            listener = listener,
            trezor = trezor,
            refresh = refresh,
            json = jsonTypeData.json,
            derivationPath = account.derivationPath,
        ).bind()

        ensure(signature.signer == address) {
            TrezorFailure.InvalidState()
        }

        signature
    }

    override suspend fun sign(
        listener: TrezorListener,
        trezor: Trezor,
        refresh: Boolean,
        address: Address,
        message: EthMessage,
    ): Either<TrezorFailure, Signature> = either {

        val account = appStateRepo.optic(AppState.account(address)).firstOrNull()
            ?: raise(TrezorFailure.Other())

        val signature = trezorRepo.signPersonalMessage(
            listener = listener,
            trezor = trezor,
            refresh = refresh,
            message = message.value,
            derivationPath = account.derivationPath,
        ).bind()

        ensure(signature.signer == address) {
            TrezorFailure.InvalidState()
        }

        signature
    }

    override suspend fun sign(
        listener: TrezorListener,
        trezor: Trezor,
        refresh: Boolean,
        transaction: TransactionData,
    ): Either<TrezorFailure, Signature> = either {

        val account = appStateRepo.optic(AppState.account(transaction.tx.from)).firstOrNull()
            ?: raise(TrezorFailure.Other())

        val signature = trezorRepo.signTransaction(
            listener = listener,
            trezor = trezor,
            transaction = transaction,
            refresh = refresh,
            derivationPath = account.derivationPath,
        ).bind()

        ensure(signature.signer == transaction.tx.from) {
            TrezorFailure.InvalidState()
        }

        signature
    }
}

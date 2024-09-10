package kosh.domain.usecases.transaction.parser

import arrow.core.raise.either
import kosh.domain.failure.Web3Failure
import kosh.domain.models.Address
import kosh.domain.models.ByteString
import kosh.domain.models.ChainId
import kosh.domain.models.funSelector
import kosh.domain.models.web3.ContractCall
import kosh.domain.serializers.BigInteger
import kosh.domain.serializers.Either
import kosh.domain.state.AppState
import kosh.domain.state.AppStateProvider
import kosh.domain.state.nativeToken
import kosh.domain.state.network
import kosh.domain.utils.optic

class FallbackParser(
    private val appStateProvider: AppStateProvider,
) : ContractCallParser {

    override suspend fun parse(
        chainId: ChainId,
        from: Address,
        to: Address?,
        value: BigInteger,
        input: ByteString,
    ): Either<Web3Failure, ContractCall> = either {
        val network = appStateProvider.optic(AppState.network((chainId))).value
            ?: raise(Web3Failure.NetworkNotSupported())
        val token = appStateProvider.optic(AppState.nativeToken(network.id)).value
            ?: raise(Web3Failure.NetworkNotSupported())

        when {
            to != null && !value.isZero() && input.bytes().size == 0 -> {
                ContractCall.NativeTransfer(
                    token = token,
                    destination = to,
                    amount = value,
                )
            }

            to != null -> {
                ContractCall.Fallback(
                    token = token,
                    from = from,
                    contract = to,
                    input = input,
                    value = value,
                    selector = input.funSelector()
                )
            }

            else -> {
                ContractCall.Deploy(
                    token = token,
                    from = from,
                    input = input,
                    value = value,
                )
            }
        }
    }
}

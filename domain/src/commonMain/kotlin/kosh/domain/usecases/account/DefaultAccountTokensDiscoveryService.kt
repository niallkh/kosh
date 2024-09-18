package kosh.domain.usecases.account

import arrow.core.IorNel
import arrow.core.nel
import arrow.core.raise.forEachAccumulating
import arrow.core.raise.iorNel
import arrow.core.raise.withError
import kosh.domain.entities.AccountEntity
import kosh.domain.failure.AccountFailure
import kosh.domain.failure.Web3Failure
import kosh.domain.models.token.map
import kosh.domain.state.AppState
import kosh.domain.state.AppStateProvider
import kosh.domain.state.account
import kosh.domain.state.activeTokens
import kosh.domain.usecases.token.TokenBalanceService
import kosh.domain.usecases.token.TokenDiscoveryService
import kosh.domain.usecases.token.TokenService
import kosh.domain.utils.optic
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.toList

class DefaultAccountTokensDiscoveryService(
    private val tokensDiscoveryService: TokenDiscoveryService,
    private val tokenBalanceService: TokenBalanceService,
    private val tokenService: TokenService,
    private val appStateProvider: AppStateProvider,
) : AccountTokensDiscoveryService {

    override suspend fun discoverTokens(id: AccountEntity.Id): IorNel<Web3Failure, Unit> = iorNel {
        val account = appStateProvider.optic(AppState.account(id)).value
            ?: raise(Web3Failure.Other(AccountFailure.NotFound()).nel())

        val activeTokens = appStateProvider.optic(AppState.activeTokens()).value
            .asSequence().map { it.address }

        val verifiedTokens = tokensDiscoveryService.getVerifiedTokens()
            .filter { it.address !in activeTokens }
            .toList()

        val balances = tokenBalanceService.getBalances(account.address, verifiedTokens).bind()

        val newTokens = verifiedTokens.zip(balances)
            .filter { (_, balance) -> !balance.value.isZero() }
            .map { it.first }

        forEachAccumulating(newTokens) { token ->
            withError({ Web3Failure.Other(it.message) }) {
                tokenService.add(
                    chainId = token.chainId,
                    address = token.address,
                    name = token.name,
                    symbol = token.symbol,
                    decimals = token.decimals,
                    icon = token.icon,
                    type = token.type.map(),
                    tokenId = null,
                    tokenName = null,
                    uri = null,
                    image = null,
                    external = null,
                ).bind()
            }
        }
    }
}

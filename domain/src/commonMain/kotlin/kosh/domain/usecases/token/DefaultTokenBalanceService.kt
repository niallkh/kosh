package kosh.domain.usecases.token

import arrow.core.Ior
import arrow.core.raise.forEachAccumulating
import arrow.core.raise.iorNel
import arrow.fx.coroutines.parMap
import arrow.optics.dsl.at
import arrow.optics.typeclasses.At
import kosh.domain.entities.TokenEntity
import kosh.domain.failure.Web3Failure
import kosh.domain.models.token.AccountBalance
import kosh.domain.models.token.Balance
import kosh.domain.models.token.BalanceFilters
import kosh.domain.models.token.TokenBalance
import kosh.domain.repositories.AppStateRepo
import kosh.domain.repositories.TokenBalanceRepo
import kosh.domain.repositories.modify
import kosh.domain.repositories.optic
import kosh.domain.serializers.ImmutableList
import kosh.domain.serializers.Nel
import kosh.domain.state.AppState
import kosh.domain.state.activeAccounts
import kosh.domain.state.activeTokens
import kosh.domain.state.tokenBalances
import kosh.domain.utils.optic
import kosh.domain.utils.phmap
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentMap
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class DefaultTokenBalanceService(
    private val appStateRepo: AppStateRepo,
    private val tokenBalanceRepo: TokenBalanceRepo,
) : TokenBalanceService {

    override fun getBalances(
        filters: BalanceFilters,
    ): Flow<ImmutableList<TokenBalance>> = combine(
        appStateRepo.optic(AppState.activeTokens()),
        appStateRepo.optic(AppState.tokenBalances),
    ) { tokens, balances ->
        tokens
            .filter { token -> filters.networks?.contains(token.networkId) ?: true }
            .map { token ->
                val tokenBalance = balances
                    .filter { (accountId) -> filters.filterAccounts?.contains(accountId) ?: true }
                    .map { (_, balances) -> balances[token.id] ?: Balance() }
                    .fold(Balance()) { total, balance -> total.copy(value = total.value + balance.value) }

                TokenBalance(
                    token = token,
                    value = tokenBalance,
                )
            }
            .toImmutableList()
    }

    override fun getTokenBalances(
        id: TokenEntity.Id,
        hideZeros: Boolean,
    ): Flow<List<AccountBalance>> = combine(
        appStateRepo.state.optic(AppState.activeAccounts()),
        appStateRepo.state.optic(AppState.tokenBalances)
    ) { accounts, tokenBalances ->
        accounts.mapNotNull { account ->
            val balance = tokenBalances[account.id]?.get(id) ?: Balance()

            if (hideZeros && balance.value.isZero()) {
                null
            } else {
                AccountBalance(
                    account = account,
                    value = balance,
                )
            }
        }
    }

    override suspend fun update(): Ior<Nel<Web3Failure>, Unit> = iorNel {
        val accounts = appStateRepo.state.optic(AppState.activeAccounts()).value
        val tokens = appStateRepo.state.optic(AppState.activeTokens()).value

        forEachAccumulating(accounts) { account ->
            val tokensByChain = tokens.groupBy { it.networkId }

            val balanceByToken = tokensByChain.entries.parMap { (networkId, tokens) ->
                val balances = tokenBalanceRepo.getBalances(
                    networkId = networkId,
                    account = account.address,
                    tokens = tokens,
                ).bind()

                tokens.zip(balances)
            }
                .flatten()
                .associate { (token, balance) -> token.id to balance }

            appStateRepo.modify {
                AppState.tokenBalances.at(At.phmap(), account.id) set
                        balanceByToken.toPersistentMap()
            }
        }
    }
        .mapLeft { failures -> failures.distinctBy { it::class } }
}

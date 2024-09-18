package kosh.domain.usecases.token

import arrow.core.Ior
import arrow.core.mapOrAccumulate
import arrow.core.raise.iorNel
import arrow.fx.coroutines.parMapOrAccumulate
import arrow.optics.dsl.at
import arrow.optics.typeclasses.At
import kosh.domain.failure.Web3Failure
import kosh.domain.models.Address
import kosh.domain.models.token.Balance
import kosh.domain.models.token.TokenMetadata
import kosh.domain.repositories.AppStateRepo
import kosh.domain.repositories.TokenBalanceRepo
import kosh.domain.repositories.modify
import kosh.domain.serializers.Nel
import kosh.domain.state.AppState
import kosh.domain.state.activeAccounts
import kosh.domain.state.activeTokens
import kosh.domain.state.tokenBalances
import kosh.domain.utils.optic
import kosh.domain.utils.phmap
import kotlinx.collections.immutable.toPersistentMap

class DefaultTokenBalanceService(
    private val appStateRepo: AppStateRepo,
    private val tokenBalanceRepo: TokenBalanceRepo,
) : TokenBalanceService {

    override suspend fun update(): Ior<Nel<Web3Failure>, Unit> = iorNel {
        val accounts = appStateRepo.state.optic(AppState.activeAccounts()).value
        val tokens = appStateRepo.state.optic(AppState.activeTokens()).value

        accounts.mapOrAccumulate { account ->
            val tokensByChain = tokens.groupBy { it.networkId }

            val balancesByToken = tokensByChain.entries.parMapOrAccumulate { (networkId, tokens) ->
                val balances = tokenBalanceRepo.getBalances(
                    networkId = networkId,
                    account = account.address,
                    tokens = tokens,
                ).bind()

                tokens.zip(balances)
            }.bindNel()
                .flatten()
                .associate { (token, balance) -> token.id to balance }

            appStateRepo.modify {
                AppState.tokenBalances.at(
                    At.phmap(),
                    account.id
                ) set balancesByToken.toPersistentMap()
            }
        }.bind()
    }

    override suspend fun getBalances(
        account: Address,
        tokens: List<TokenMetadata>,
    ): Ior<Nel<Web3Failure>, List<Balance>> = iorNel {

        val tokensByChain = tokens.groupBy { it.chainId }

        val balancesByToken = tokensByChain.entries.parMapOrAccumulate { (chainId, tokens) ->
            val balances = tokenBalanceRepo.getBalances(
                chainId = chainId,
                account = account,
                tokens = tokens,
            ).bind()

            tokens.zip(balances)
        }.bind()
            .flatten()
            .associate { (token, balance) -> token to balance }

        tokens.map { balancesByToken.getValue(it) }
    }
}

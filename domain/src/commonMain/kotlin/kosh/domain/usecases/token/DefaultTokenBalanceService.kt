package kosh.domain.usecases.token

import arrow.core.Ior
import arrow.core.raise.either
import arrow.core.raise.iorNel
import arrow.fx.coroutines.parMap
import arrow.optics.dsl.at
import arrow.optics.typeclasses.At
import co.touchlab.kermit.Logger
import kosh.domain.entities.AccountEntity
import kosh.domain.entities.NetworkEntity
import kosh.domain.entities.TokenEntity
import kosh.domain.failure.Web3Failure
import kosh.domain.models.Address
import kosh.domain.models.token.Balance
import kosh.domain.models.token.TokenMetadata
import kosh.domain.repositories.AppStateRepo
import kosh.domain.repositories.TokenBalanceRepo
import kosh.domain.serializers.Nel
import kosh.domain.state.AppState
import kosh.domain.state.activeAccounts
import kosh.domain.state.activeTokens
import kosh.domain.state.network
import kosh.domain.state.tokenBalances
import kosh.domain.utils.accumulate
import kosh.domain.utils.phmap
import kotlinx.collections.immutable.toPersistentMap

class DefaultTokenBalanceService(
    private val appStateRepo: AppStateRepo,
    private val tokenBalanceRepo: TokenBalanceRepo,
) : TokenBalanceService {

    private val logger = Logger.withTag("[K]TokenBalanceService")

    override suspend fun update(): Ior<Nel<Web3Failure>, Unit> = iorNel {
        logger.i { "update()" }
        val state = appStateRepo.state
        val accounts = AppState.activeAccounts().get(state)
        val tokens = AppState.activeTokens().get(state)

        accounts.parMap { account ->
            val tokensByChain = tokens.groupBy { it.networkId }

            val balancesByToken = tokensByChain.entries.parMap { (networkId, tokens) ->
                val balances = getBalances(networkId, account, tokens)
                accumulate(balances) { emptyList() }
            }
                .flatten()
                .associate { (token, balance) -> token.id to balance }

            appStateRepo.update {
                AppState.tokenBalances.at(
                    At.phmap(),
                    account.id
                ) set balancesByToken.toPersistentMap()
            }
        }
    }

    private suspend fun getBalances(
        networkId: NetworkEntity.Id,
        account: AccountEntity,
        tokens: List<TokenEntity>,
    ) = either {
        val balances = tokenBalanceRepo.getBalances(
            networkId = networkId,
            account = account.address,
            tokens = tokens,
        ).bind()

        tokens.zip(balances)
    }

    override suspend fun getBalances(
        account: Address,
        tokens: List<TokenMetadata>,
    ): Ior<Nel<Web3Failure>, List<Balance>> = iorNel {
        logger.i { "getBalances()" }

        val tokensByChain = tokens
            .mapNotNull { token ->
                AppState.network(token.chainId).get(appStateRepo.state)?.id?.let { token to it }
            }
            .groupBy({ (_, chainId) -> chainId }, { (token, _) -> token })

        val balancesByToken = tokensByChain.entries.parMap { (networkId, tokens) ->
            val balances = getBalances(networkId, account, tokens)
            accumulate(balances) { emptyList() }
        }
            .flatten()
            .associate { (token, balance) -> token to balance }

        tokens.map { balancesByToken.getOrElse(it) { Balance() } }
    }

    private suspend fun getBalances(
        networkId: NetworkEntity.Id,
        account: Address,
        tokens: List<TokenMetadata>,
    ) = either {
        val balances = tokenBalanceRepo.getBalancesForMetadata(
            networkId = networkId,
            account = account,
            tokens = tokens,
        ).bind()

        tokens.zip(balances)
    }
}

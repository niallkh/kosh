@file:UseSerializers(
    PersistentMapSerializer::class,
    PersistentHashSetSerializer::class,
)

package kosh.domain.state

import arrow.optics.Getter
import arrow.optics.dsl.at
import arrow.optics.dsl.index
import arrow.optics.optics
import arrow.optics.typeclasses.At
import arrow.optics.typeclasses.Index
import kosh.domain.entities.AccountEntity
import kosh.domain.entities.NetworkEntity
import kosh.domain.entities.TokenEntity
import kosh.domain.entities.TransactionEntity
import kosh.domain.entities.WalletEntity
import kosh.domain.entities.accountId
import kosh.domain.entities.networkId
import kosh.domain.models.Address
import kosh.domain.models.ChainId
import kosh.domain.models.account.ethereumAddressIndex
import kosh.domain.models.account.ledgerAddressIndex
import kosh.domain.models.token.Balance
import kosh.domain.models.token.TokenBalance
import kosh.domain.serializers.BigInteger
import kosh.domain.serializers.PersistentHashSet
import kosh.domain.serializers.PersistentHashSetSerializer
import kosh.domain.serializers.PersistentMap
import kosh.domain.serializers.PersistentMapSerializer
import kosh.domain.utils.pmap
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentHashMapOf
import kotlinx.collections.immutable.persistentHashSetOf
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toPersistentHashSet
import kotlinx.collections.immutable.toPersistentList
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import okio.Buffer

@optics
@Serializable
data class AppState(
    val wallets: PersistentMap<WalletEntity.Id, WalletEntity> = persistentMapOf(),
    val accounts: PersistentMap<AccountEntity.Id, AccountEntity> = persistentMapOf(),
    val networks: PersistentMap<NetworkEntity.Id, NetworkEntity> = persistentMapOf(),
    val tokens: PersistentMap<TokenEntity.Id, TokenEntity> = persistentMapOf(),
    val transactions: PersistentMap<TransactionEntity.Id, TransactionEntity> = persistentMapOf(),

    val enabledAccountIds: PersistentHashSet<AccountEntity.Id> = persistentHashSetOf(),
    val enabledNetworkIds: PersistentHashSet<NetworkEntity.Id> = persistentHashSetOf(),

    val tokenBalances: PersistentMap<AccountEntity.Id, PersistentMap<TokenEntity.Id, Balance>> = persistentHashMapOf(),

    val version: Int = 0,
) {

    companion object {
        val Default = AppState()
    }
}

fun AppState.Companion.activeNetworks() = Getter<AppState, PersistentList<NetworkEntity>> { state ->
    state.networks.values
        .filter { token -> token.id in state.enabledNetworkIds }
        .sortedWith(compareBy<NetworkEntity> { !it.testnet }.thenBy { it.createdAt })
        .toPersistentList()
}

fun AppState.Companion.activeNetworksIds() =
    activeNetworks() compose Getter { list -> list.map { it.id }.toPersistentHashSet() }

fun AppState.Companion.networks() = Getter<AppState, PersistentList<NetworkEntity>> { state ->
    state.networks.values
        .sortedWith(compareBy<NetworkEntity> { it.testnet }.thenBy { it.createdAt })
        .toPersistentList()
}

fun AppState.Companion.activeAccounts() = Getter<AppState, PersistentList<AccountEntity>> { state ->
    state.accounts.values
        .filter { acc -> acc.id in state.enabledAccountIds }
        .sortedBy { it.createdAt }
        .toPersistentList()
}

fun AppState.Companion.activeAccountIds() =
    activeAccounts() compose Getter { list -> list.map { it.id }.toPersistentHashSet() }


fun AppState.Companion.activeTokens() = Getter<AppState, PersistentList<TokenEntity>> { state ->
    state.tokens.values
        .filter { token -> token.networkId in state.enabledNetworkIds }
        .sortedBy { it.createdAt }
        .toPersistentList()
}

fun AppState.Companion.activeTransactions() =
    Getter<AppState, PersistentList<TransactionEntity>> { state ->
        state.transactions.values
            .filter { tx -> tx.networkId?.let { it in state.enabledNetworkIds } ?: true }
            .filter { tx -> tx.accountId?.let { it in state.enabledAccountIds } ?: true }
            .sortedByDescending { it.createdAt }
            .toPersistentList()
    }

fun AppState.Companion.accountsByWallet() =
    Getter<AppState, PersistentList<Pair<WalletEntity, PersistentList<AccountEntity>>>> { state ->
        state.wallets
            .map { (walletId, wallet) ->
                wallet to AppState.accounts(walletId).get(state)
            }
            .sortedBy { it.first.createdAt }
            .toPersistentList()
    }

fun AppState.Companion.account(id: AccountEntity.Id) = AppState.accounts.at(At.pmap(), id)

fun AppState.Companion.account(address: Address) = account(AccountEntity.Id(address))

fun AppState.Companion.accounts(id: WalletEntity.Id) = AppState.accounts compose Getter { map ->
    map.values
        .filter { acc -> acc.walletId == id }
        .sortedWith(
            compareBy(
                { it.derivationPath.ledgerAddressIndex },
                { it.derivationPath.ethereumAddressIndex }
            )
        )
        .toPersistentList()
}

fun AppState.Companion.optionalAccount(id: AccountEntity.Id) =
    AppState.accounts.index(Index.pmap(), id)

fun AppState.Companion.wallet(id: WalletEntity.Id) = AppState.wallets.at(At.pmap(), id)

fun AppState.Companion.token(id: TokenEntity.Id) = AppState.tokens.at(At.pmap(), id)

fun AppState.Companion.nativeToken(id: NetworkEntity.Id) =
    AppState.tokens.at(At.pmap(), TokenEntity.Id(id))

fun AppState.Companion.erc20Token(id: NetworkEntity.Id, address: Address) =
    AppState.tokens.at(At.pmap(), TokenEntity.Id(id, address))

fun AppState.Companion.nftToken(
    id: NetworkEntity.Id, address: Address, tokenId: BigInteger,
) = AppState.tokens.at(At.pmap(), TokenEntity.Id(id, address, tokenId))

fun AppState.Companion.optionalToken(id: TokenEntity.Id) =
    AppState.tokens.index(Index.pmap(), id)

fun AppState.Companion.network(id: NetworkEntity.Id) = AppState.networks.at(At.pmap(), id)

fun AppState.Companion.network(chainId: ChainId) = network(NetworkEntity.Id(chainId))

fun AppState.Companion.transaction(id: TransactionEntity.Id) =
    AppState.transactions.at(At.pmap(), id)

fun AppState.Companion.optionalTransaction(id: TransactionEntity.Id) =
    AppState.transactions.index(Index.pmap(), id)

fun AppState.Companion.optionalNetwork(id: NetworkEntity.Id) =
    AppState.networks.index(Index.pmap(), id)

fun AppState.Companion.balances() = Getter<AppState, PersistentList<TokenBalance>> { state ->
    val activeAccounts = AppState.activeAccountIds().get(state)

    AppState.activeTokens().get(state)
        .map { token ->

            val tokenBalance = state.tokenBalances
                .filter { (accountId) -> accountId in activeAccounts }
                .map { (_, balances) -> balances[token.id] ?: Balance() }
                .fold(Balance()) { total, balance -> total.copy(value = total.value + balance.value) }

            TokenBalance(
                token = token,
                value = tokenBalance,
            )
        }
        .toPersistentList()
}

fun AppState.Companion.balancesKey() = Getter<AppState, String> { state ->
    AppState.activeTokens().get(state).asSequence().map { it.id.value }
        .flatMap { AppState.activeAccounts().get(state) }.map { it.id.value }
        .fold(Buffer()) { buffer, uuid ->
            buffer
                .writeLong(uuid.mostSignificantBits)
                .writeLong(uuid.leastSignificantBits)
        }
        .md5()
        .base64()
}

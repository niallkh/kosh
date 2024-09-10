package kosh.libs.ledger.cmds

class SignTransactionParameters(
    val plugins: List<PluginInfo> = listOf(),
    val externalPlugins: List<ExternalPluginInfo> = listOf(),
    val tokens: List<TokenInfo> = listOf(),
    val nfts: List<NftInfo> = listOf(),
    val domains: List<DomainInfo> = listOf(),
)

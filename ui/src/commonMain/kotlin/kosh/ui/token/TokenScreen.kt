package kosh.ui.token

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import arrow.core.raise.nullable
import com.ionspin.kotlin.bignum.integer.BigInteger.Companion.ZERO
import kosh.domain.entities.TokenEntity
import kosh.domain.entities.TokenEntity.Type
import kosh.domain.entities.isNft
import kosh.domain.models.Uri
import kosh.domain.models.orZero
import kosh.domain.models.token.AccountBalance
import kosh.domain.models.token.NftExtendedMetadata
import kosh.domain.serializers.ImmutableList
import kosh.presentation.network.rememberNetwork
import kosh.presentation.network.rememberOpenExplorer
import kosh.presentation.token.NftMetadataState
import kosh.presentation.token.TokenBalancesState
import kosh.presentation.token.TokenState
import kosh.presentation.token.rememberNftMetadata
import kosh.presentation.token.rememberOpenSea
import kosh.presentation.token.rememberRefreshToken
import kosh.presentation.token.rememberToken
import kosh.presentation.token.rememberTokenBalances
import kosh.ui.component.LoadingIndicator
import kosh.ui.component.icon.TokenIcon
import kosh.ui.component.image.NftImage
import kosh.ui.component.items.AccountBalanceItem
import kosh.ui.component.menu.AdaptiveMoreMenu
import kosh.ui.component.placeholder.placeholder
import kosh.ui.component.scaffold.KoshScaffold
import kosh.ui.component.single.single
import kosh.ui.component.text.KeyValueRow
import kosh.ui.component.text.TextAddressShort
import kosh.ui.component.text.TextHeader
import kosh.ui.component.text.TextLine
import kosh.ui.component.text.TextNumber
import kosh.ui.component.text.TextUri
import kosh.ui.failure.AppFailureMessage
import kosh.ui.resources.icons.OpenSea
import kotlinx.collections.immutable.persistentListOf

@Composable
fun TokenScreen(
    modifier: Modifier = Modifier,
    id: TokenEntity.Id,
    onNavigateUp: () -> Unit,
    onDelete: (TokenEntity.Id) -> Unit,
) {
    val token = rememberToken(id)

    val nftMetadata = nullable {
        rememberNftMetadata(
            uri = ensureNotNull(token.entity?.uri),
            tokenId = ensureNotNull(token.entity?.tokenId)
        )
    }

    val refreshToken = rememberRefreshToken(id)

    KoshScaffold(
        modifier = modifier,
        title = {
            TextLine(
                token.entity?.name.orEmpty(),
                Modifier.placeholder(token.entity == null),
            )
        },
        onNavigateUp = onNavigateUp,
        actions = {
            TokenIcon(
                token = token.entity,
                modifier = Modifier
                    .size(40.dp)
            )

            if (token.entity?.type != Type.Native) {
                TokenMoreMenu(
                    token = token.entity,
                    onDelete = { onDelete(id) },
                    onRefresh = {
                        refreshToken.refresh()
                        nftMetadata?.run { refresh() }
                    }
                )
            } else {
                Spacer(Modifier.width(8.dp))
            }
        }
    ) { paddingValues ->
        val tokenBalances = rememberTokenBalances(id)

        AppFailureMessage(refreshToken.web3Failure) {
            refreshToken.refresh()
        }

        AppFailureMessage(refreshToken.tokenFailure)

        TokenContent(
            paddingValues = paddingValues,
            token = token,
            nftMetadata = nftMetadata,
            tokenBalances = tokenBalances,
            id = id
        )

        LoadingIndicator(
            refreshToken.loading || nftMetadata?.loading == true,
            Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun TokenContent(
    id: TokenEntity.Id,
    token: TokenState,
    nftMetadata: NftMetadataState?,
    tokenBalances: TokenBalancesState,
    paddingValues: PaddingValues,
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(paddingValues)
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        if (token.entity?.isNft == true) {
            NftItem(
                modifier = Modifier
                    .animateContentSize()
                    .padding(horizontal = 16.dp),
                tokenNameStyle = MaterialTheme.typography.titleLarge,
                tokenName = token.entity?.tokenName ?: token.entity?.name.orEmpty(),
                image = token.entity?.image!!,
            ) {

                Text(
                    token.entity?.name.orEmpty(),
                    style = MaterialTheme.typography.bodyLarge,
                )

                if (nftMetadata?.nft == null
                    || nftMetadata.nft != null && nftMetadata.nft?.description != null
                ) {
                    Text(
                        nftMetadata?.nft?.description
                            ?: "Nft description id loading. ".repeat(5),
                        Modifier.placeholder(nftMetadata?.nft == null),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }

        if (tokenBalances.balances.isNotEmpty()) {
            BalancesSection(id, tokenBalances.balances)
        }

        if (nftMetadata?.nft?.attributes?.isNotEmpty() == true) {
            AttributesSection(
                nftMetadata.nft?.attributes ?: persistentListOf()
            )
        }

        InfoSection(id)
    }
}

@Composable
private fun TokenMoreMenu(
    token: TokenEntity?,
    onDelete: (TokenEntity) -> Unit,
    onRefresh: () -> Unit,
) {
    AdaptiveMoreMenu { dismiss ->

        val uriHandler = LocalUriHandler.current

        token?.external?.let {
            DropdownMenuItem(
                text = { Text("Open") },
                onClick = { dismiss { uriHandler.openUri(it.toString()) } },
                leadingIcon = {
                    Icon(
                        Icons.Default.OpenInBrowser,
                        contentDescription = "Open"
                    )
                }
            )
        }

        if (token != null && token.type != Type.Native) {
            val openExplorer = rememberOpenExplorer(token.networkId)

            DropdownMenuItem(
                text = { Text("Open In Explorer") },
                onClick = { dismiss { token.address?.let { openExplorer.openToken(it) } } },
                leadingIcon = {
                    Icon(Icons.AutoMirrored.Filled.OpenInNew, "Open in explorer")
                }
            )
        }

        token?.let { token ->
            if (token.isNft) {
                val openSea = rememberOpenSea()
                val network = rememberNetwork(token.networkId)

                DropdownMenuItem(
                    text = { Text("Open in OpenSea") },
                    onClick = {
                        dismiss {
                            nullable {
                                openSea.openNft(
                                    ensureNotNull(network.entity?.chainId),
                                    ensureNotNull(token.address),
                                    ensureNotNull(token.tokenId)
                                )
                            }
                        }
                    },
                    leadingIcon = {
                        Image(
                            OpenSea,
                            "Open In OpenSea",
                            Modifier.size(24.dp),
                        )
                    }
                )
            }
        }

        token?.let { token ->
            if (token.type != Type.Native) {
                DropdownMenuItem(
                    text = { Text("Refresh") },
                    onClick = { dismiss { onRefresh() } },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Refresh,
                            contentDescription = "Open"
                        )
                    }
                )
            }
        }

        token?.let { token ->
            if (token.type != Type.Native) {
                DropdownMenuItem(
                    text = { Text("Delete") },
                    onClick = { dismiss { onDelete(token) } },
                    leadingIcon = {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                )
            }
        }
    }
}

@Composable
private fun InfoSection(
    id: TokenEntity.Id,
) {
    val token = rememberToken(id)

    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        TextHeader(
            text = "Info",
        )

        KeyValueRow(
            modifier = Modifier.fillMaxWidth(),
            key = { TextLine("Type") },
            value = { TextLine(token.entity?.type?.name?.uppercase().orEmpty()) }
        )

        val network = token.entity?.networkId?.let { rememberNetwork(it) }

        KeyValueRow(
            modifier = Modifier.fillMaxWidth(),
            key = { TextLine("Network") },
            value = {
                TextLine(
                    network?.entity?.name.orEmpty(),
                    Modifier.placeholder(network?.entity == null),
                )
            }
        )

        if (token.entity?.address != null) {
            KeyValueRow(
                modifier = Modifier.fillMaxWidth(),
                key = { TextLine(text = "Address") },
                value = {
                    TextAddressShort(
                        token.entity?.address.orZero(),
                        Modifier.placeholder(token.entity == null),
                        clickable = true,
                    )
                }
            )
        }

        if (token.entity?.isNft == true) {
            KeyValueRow(
                modifier = Modifier.fillMaxWidth(),
                key = { TextLine(text = "Token ID") },
                value = {
                    TextNumber(
                        token.entity?.tokenId ?: ZERO,
                        Modifier.placeholder(token.entity == null),
                        clickable = true,
                    )
                }
            )

            KeyValueRow(
                modifier = Modifier.fillMaxWidth(),
                key = { TextLine(text = "Uri") },
                value = {
                    TextUri(
                        token.entity?.uri ?: Uri(),
                        Modifier.placeholder(token.entity == null),
                        maxWidth = 240.dp,
                        clickable = true,
                    )
                }
            )

            KeyValueRow(
                modifier = Modifier.fillMaxWidth(),
                key = { TextLine(text = "Image") },
                value = {
                    TextUri(
                        token.entity?.image ?: Uri(),
                        Modifier.placeholder(token.entity == null),
                        maxWidth = 240.dp,
                        clickable = true,
                    )
                }
            )

            KeyValueRow(
                modifier = Modifier.fillMaxWidth(),
                key = { TextLine(text = "Website") },
                value = {
                    TextUri(
                        token.entity?.external ?: Uri(),
                        Modifier.placeholder(token.entity == null),
                        maxWidth = 240.dp,
                        clickable = true,
                    )
                }
            )
        }
    }
}

@Composable
private fun AttributesSection(
    attributes: ImmutableList<NftExtendedMetadata.Attribute>,
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        TextHeader(
            text = "Attributes",
        )

        for ((index, attribute) in attributes.withIndex()) {
            key(index) {
                KeyValueRow(
                    modifier = Modifier.fillMaxWidth(),
                    key = {
                        TextLine(attribute.traitType.orEmpty())
                    },
                    value = {
                        TextLine(attribute.value.orEmpty())
                    }
                )
            }
        }
    }
}

@Composable
private fun BalancesSection(
    id: TokenEntity.Id,
    balances: List<AccountBalance>,
) {
    val token = rememberToken(id)

    Column {
        TextHeader(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "Balances",
        )

        for (balance in balances) {
            key(balance.account.id.value.leastSignificantBits) {
                AccountBalanceItem(
                    account = balance.account,
                    token = token.entity,
                    balance = balance.value,
                )
            }
        }
    }
}

@Composable
private fun NftItem(
    modifier: Modifier = Modifier,
    tokenName: String,
    tokenNameStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    image: Uri,
    onClick: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit = {},
) {
    OutlinedCard(
        modifier = modifier,
        onClick = onClick.single()
    ) {

        NftImage(
            image = image,
            maxSize = 1024,
        )


        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = tokenName,
                    style = tokenNameStyle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            content()
        }
    }
}

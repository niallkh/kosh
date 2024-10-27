package kosh.ui.component.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kosh.domain.entities.NetworkEntity
import kosh.domain.entities.TokenEntity
import kosh.domain.models.Uri
import kosh.domain.models.token.NftMetadata
import kosh.domain.models.token.TokenMetadata
import kosh.presentation.network.rememberNetwork
import kosh.ui.component.icon.ChainBadge
import kosh.ui.component.image.NftImage
import kosh.ui.component.single.single
import kosh.ui.component.text.TextLine

@Composable
fun NftCard(
    token: TokenEntity?,
    network: NetworkEntity? = token?.networkId?.let { rememberNetwork(it) }?.entity,
    maxSize: Int = 512,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    trailingContent: (@Composable RowScope.() -> Unit)? = null,
) {
    NftCard(
        image = token?.image,
        name = token?.tokenName ?: token?.name,
        placeholder = token == null,
        modifier = modifier,
        maxSize = maxSize,
        onClick = onClick,
        network = network,
        trailingContent = trailingContent
    )
}

@Composable
fun NftCard(
    token: TokenMetadata?,
    nft: NftMetadata?,
    network: NetworkEntity? = nft?.chainId?.let { rememberNetwork(it) }?.entity,
    maxSize: Int = 512,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    trailingContent: (@Composable RowScope.() -> Unit)? = null,
) {
    NftCard(
        image = nft?.image,
        name = nft?.name ?: token?.name,
        placeholder = nft == null,
        modifier = modifier,
        maxSize = maxSize,
        onClick = onClick,
        network = network,
        trailingContent = trailingContent
    )
}

@Composable
private fun NftCard(
    name: String?,
    image: Uri?,
    network: NetworkEntity?,
    placeholder: Boolean,
    maxSize: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    trailingContent: @Composable() (RowScope.() -> Unit)?,
) {
    OutlinedCard(
        modifier = modifier,
        onClick = onClick.single(),
        enabled = !placeholder,
    ) {

        Box {
            NftImage(
                image = image,
                maxSize = maxSize
            )

            ChainBadge(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp),
                network = network
            )
        }

        CompositionLocalProvider(
            LocalTextStyle provides MaterialTheme.typography.bodyLarge
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                TextLine(
                    modifier = Modifier.weight(1f),
                    text = name,
                )

                trailingContent?.invoke(this)
            }
        }
    }
}

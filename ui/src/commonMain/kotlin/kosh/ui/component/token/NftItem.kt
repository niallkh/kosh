package kosh.ui.component.token

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kosh.domain.models.ChainId
import kosh.domain.models.Uri
import kosh.ui.component.icon.ChainBadge
import kosh.ui.component.image.NftImage
import kosh.ui.component.single.single

@Composable
fun NftItem(
    modifier: Modifier = Modifier,
    tokenName: String,
    tokenNameStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    image: Uri,
    chainId: ChainId?,
    networkIcon: Uri?,
    networkName: String?,
    onClick: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit = {},
) {
    OutlinedCard(
        modifier = modifier,
        onClick = onClick.single()
    ) {
        Box {
            NftImage(
                image = image,
                maxSize = 1024,
            )

            if (chainId != null) {
                ChainBadge(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp),
                    chainId = chainId,
                    symbol = networkName ?: "",
                    icon = networkIcon,
                )
            }
        }

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

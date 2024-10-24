package kosh.ui.component.dapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.model.ImageRequest
import com.seiko.imageloader.rememberImagePainter
import kosh.domain.entities.NetworkEntity
import kosh.domain.entities.TransactionEntity.Dapp
import kosh.domain.models.Uri
import kosh.domain.models.reown.DappMetadata
import kosh.presentation.network.rememberNetwork
import kosh.ui.component.colors.uriColor
import kosh.ui.component.icon.ChainBadge
import kosh.ui.component.icon.IconPainter
import kosh.ui.component.placeholder.placeholder
import kosh.ui.resources.icons.DappUnknown

@Composable
fun DappIcon(
    dapp: Dapp?,
    modifier: Modifier = Modifier,
    networkId: NetworkEntity.Id? = null,
) {
    DappIcon(
        placeholder = dapp == null,
        dapp = dapp?.url,
        icon = dapp?.icon,
        networkId = networkId,
        modifier = modifier
    )
}

@Composable
fun DappIcon(
    dapp: DappMetadata?,
    modifier: Modifier = Modifier,
    networkId: NetworkEntity.Id? = null,
) {
    DappIcon(
        placeholder = dapp == null,
        dapp = dapp?.url,
        icon = dapp?.icon,
        networkId = networkId,
        modifier = modifier
    )
}

@Composable
private fun DappIcon(
    placeholder: Boolean,
    dapp: Uri?,
    icon: Uri?,
    modifier: Modifier = Modifier,
    networkId: NetworkEntity.Id? = null,
) {
    Box {
        DappIcon(
            placeholder,
            dapp,
            icon,
            modifier,
        )

        val network = networkId?.let { rememberNetwork(it) }

        network?.let {
            ChainBadge(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(4.dp, 4.dp)
                    .placeholder(placeholder),
                network = network.entity,
            )
        }
    }
}

@Composable
private fun DappIcon(
    placeholder: Boolean,
    dapp: Uri?,
    icon: Uri?,
    modifier: Modifier = Modifier,
) {
    val request = remember(icon) {
        ImageRequest {
            data(icon)
            options { maxImageSize = 128 }
        }
    }

    val dappPainter = @Composable {
        val colorScheme = uriColor(dapp ?: Uri(), isSystemInDarkTheme())

        val painter = rememberVectorPainter(DappUnknown)

        remember(colorScheme, painter) {
            IconPainter(
                icon = painter,
                containerColor = colorScheme.secondaryContainer,
                color = colorScheme.primary,
            )
        }
    }

    Image(
        modifier = modifier
            .size(40.dp)
            .clip(MaterialTheme.shapes.extraSmall)
            .placeholder(placeholder),
        painter = rememberImagePainter(
            request = request,
            errorPainter = dappPainter
        ),
        contentDescription = "Dapp Icon",
    )
}

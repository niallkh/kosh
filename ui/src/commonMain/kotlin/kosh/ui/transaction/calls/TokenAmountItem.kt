package kosh.ui.transaction.calls

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kosh.domain.entities.TokenEntity
import kosh.domain.models.Address
import kosh.domain.models.ChainId
import kosh.domain.models.at
import kosh.domain.serializers.BigInteger
import kosh.presentation.token.rememberToken
import kosh.ui.component.icon.TokenIcon
import kosh.ui.component.menu.AdaptiveMenu
import kosh.ui.component.menu.AdaptiveMenuItem
import kosh.ui.component.text.TextAmount
import kosh.ui.component.text.TextLine
import kosh.ui.component.text.TextNumber
import kosh.ui.navigation.routes.AddTokenRoute
import kosh.ui.navigation.routes.RootRoute

@Composable
fun TokenAmountItem(
    chainId: ChainId,
    token: Address,
    amount: BigInteger,
    tokenId: BigInteger? = null,
    onOpen: (RootRoute) -> Unit,
    modifier: Modifier = Modifier,
) {
    val tokenEntity = rememberToken(chainId, token, tokenId).entity

    var menuVisible by rememberSaveable { mutableStateOf(false) }

    val supportingText: @Composable (() -> Unit)? = tokenId?.let {
        {
            Row {
                Text("Token Id: ")
                TextNumber(it)
            }
        }
    }

    ListItem(
        modifier = modifier.clickable(onClick = { menuVisible = true }),
        leadingContent = {
            if (tokenEntity != null) {
                TokenIcon(
                    token = tokenEntity,
                    modifier = modifier.size(40.dp),
                )
            } else {
                Icon(
                    Icons.Filled.AddCircle,
                    "Add",
                    Modifier.size(40.dp)
                )
            }
        },
        headlineContent = {
            TextLine(tokenEntity?.name ?: "Unknown Token")
        },
        supportingContent = supportingText,
        trailingContent = {
            TextAmount(
                token = tokenEntity,
                amount = amount
            )
        },
    )

    AdaptiveMenu(
        visible = menuVisible,
        onDismiss = { menuVisible = false }
    ) { dismiss ->
        if (tokenEntity == null) {
            AdaptiveMenuItem(
                onClick = {
                    dismiss {
                        if (tokenId == null) {
                            onOpen(
                                RootRoute.AddToken(
                                    AddTokenRoute.Search(token)
                                )
                            )
                        } else {
                            onOpen(
                                RootRoute.AddToken(
                                    AddTokenRoute.NftSearch(chainId.at(token), tokenId)
                                )
                            )
                        }
                    }
                },
                leadingIcon = { Icon(Icons.Outlined.Add, "Add Token") },
                text = { Text("Add Token") }
            )
        }
    }
}

@Composable
fun TokenAmountItem(
    tokenEntity: TokenEntity?,
    amount: BigInteger?,
    modifier: Modifier = Modifier,
    overlineContent: @Composable (() -> Unit)? = null,
) {
    ListItem(
        modifier = modifier,
        leadingContent = {
            TokenIcon(
                token = tokenEntity,
                modifier = modifier
                    .size(40.dp),
            )
        },
        headlineContent = {
            TextLine(tokenEntity?.name)
        },
        trailingContent = {
            TextAmount(
                token = tokenEntity,
                amount = amount,
            )
        },
        overlineContent = overlineContent,
    )
}

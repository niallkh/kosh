package kosh.ui.transaction

import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import kosh.domain.entities.TransactionEntity
import kosh.eth.abi.json.JsonEip712
import kosh.ui.component.dapp.DappIcon
import kosh.ui.component.path.resolve
import kosh.ui.component.placeholder.placeholder
import kosh.ui.component.single.single
import kosh.ui.component.text.TextDate
import kosh.ui.component.text.TextLine
import kosh.ui.resources.icons.TypedSignature
import kotlinx.io.bytestring.decodeToString

@Composable
fun TypedMessageItem(
    typedMessage: TransactionEntity.Eip712,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val jsonEip712 by typedMessage.jsonTypeData.resolve {
        JsonEip712.from(it.bytes().decodeToString())
    }

    ListItem(
        modifier = modifier.clickable(onClick = onClick.single()),
        overlineContent = {
            TextDate(typedMessage.createdAt)
        },
        headlineContent = {
            TextLine(
                jsonEip712?.domain?.name ?: "Typed Message",
                Modifier.placeholder(jsonEip712 == null),
            )
        },
        leadingContent = {
            DappIcon(
                typedMessage.dapp.url,
                typedMessage.dapp.icon,
                networkId = typedMessage.networkId
            )
        },
        trailingContent = {
            Icon(TypedSignature, "TypedSignature")
        },
    )
}


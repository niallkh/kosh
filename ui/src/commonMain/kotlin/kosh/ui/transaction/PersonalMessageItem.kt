package kosh.ui.transaction

import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import kosh.domain.entities.TransactionEntity
import kosh.ui.component.dapp.DappIcon
import kosh.ui.component.path.resolve
import kosh.ui.component.placeholder.placeholder
import kosh.ui.component.single.single
import kosh.ui.component.text.TextDate
import kosh.ui.component.text.TextLine
import kosh.ui.resources.icons.PersonalSignature
import kotlinx.io.bytestring.decodeToString

@Composable
fun PersonalMessageItem(
    personalMessage: TransactionEntity.PersonalMessage,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val message by personalMessage.message.resolve { it.bytes().decodeToString() }

    ListItem(
        modifier = modifier.clickable(onClick = onClick.single()),
        headlineContent = {
            TextLine(
                message ?: "Personal Message",
                Modifier.placeholder(message == null),
            )
        },
        leadingContent = {
            DappIcon(
                dapp = personalMessage.dapp.url,
                icon = personalMessage.dapp.icon
            )
        },
        trailingContent = {
            Icon(PersonalSignature, "PersonalSignature")
        },
        overlineContent = {
            TextDate(personalMessage.createdAt)
        },
    )
}


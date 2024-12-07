package kosh.ui.transaction

import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kosh.domain.entities.TransactionEntity
import kosh.domain.models.web3.EthMessage
import kosh.ui.component.dapp.DappIcon
import kosh.ui.component.path.resolve
import kosh.ui.component.placeholder.placeholder
import kosh.ui.component.single.single
import kosh.ui.component.text.TextDate
import kosh.ui.component.text.TextLine
import kosh.ui.resources.icons.PersonalSignature

@Composable
fun PersonalMessageItem(
    personalMessage: TransactionEntity.PersonalMessage?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val message = personalMessage?.message?.resolve(EthMessage.serializer())

    ListItem(
        modifier = modifier.clickable(
            enabled = personalMessage != null,
            onClick = onClick.single()
        ),
        headlineContent = {
            TextLine(message?.value)
        },
        leadingContent = {
            DappIcon(personalMessage?.dapp)
        },
        trailingContent = {
            Icon(
                PersonalSignature,
                "PersonalSignature",
                Modifier.placeholder(personalMessage == null)
            )
        },
        overlineContent = {
            TextDate(personalMessage?.createdAt)
        },
    )
}


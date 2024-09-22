package kosh.ui.transaction

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import arrow.core.raise.nullable
import kosh.domain.entities.TransactionEntity
import kosh.presentation.account.rememberAccount
import kosh.presentation.network.rememberNetwork
import kosh.presentation.transaction.rememberContractCall
import kosh.ui.component.dapp.DappIcon
import kosh.ui.component.path.resolve
import kosh.ui.component.single.single
import kosh.ui.component.text.TextDate
import kosh.ui.transaction.calls.TextFunctionName

@Composable
fun TransactionItem(
    transaction: TransactionEntity.Eip1559,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val network = rememberNetwork(transaction.networkId)
    val account = rememberAccount(transaction.sender)
    val data by transaction.data.resolve { it }

    val parsed = nullable {
        rememberContractCall(
            chainId = network.entity?.chainId ?: raise(null),
            from = account.entity?.address ?: raise(null),
            to = transaction.target,
            value = transaction.value,
            data = data ?: raise(null),
        )
    }

    ListItem(
        modifier = modifier.clickable(onClick = onClick.single()),
        overlineContent = { TextDate(transaction.receipt?.time ?: transaction.createdAt) },
        leadingContent = {
            DappIcon(
                transaction.dapp.url,
                transaction.dapp.icon,
                networkId = transaction.networkId
            )
        },
        headlineContent = {
            TextFunctionName(parsed?.contractCall)
        },
        trailingContent = {
            if (transaction.receipt != null) {
                Icon(Icons.Outlined.CheckCircle, "Confirmed")
            } else {
                Icon(Icons.Outlined.Schedule, "Pending")
            }
        }
    )
}

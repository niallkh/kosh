package kosh.ui.transaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import arrow.core.raise.nullable
import kosh.domain.entities.TransactionEntity
import kosh.domain.models.web3.ContractCall
import kosh.domain.models.web3.EthMessage
import kosh.domain.models.web3.JsonTypedData
import kosh.presentation.account.rememberAccount
import kosh.presentation.network.rememberNetwork
import kosh.presentation.network.rememberOpenExplorer
import kosh.presentation.transaction.rememberContractCall
import kosh.presentation.transaction.rememberTransaction
import kosh.ui.component.dapp.DappIcon
import kosh.ui.component.dapp.DappTitle
import kosh.ui.component.items.AccountItem
import kosh.ui.component.items.NetworkItem
import kosh.ui.component.menu.AdaptiveMenuItem
import kosh.ui.component.menu.AdaptiveMoreMenu
import kosh.ui.component.path.resolve
import kosh.ui.component.scaffold.KoshScaffold
import kosh.ui.component.single.single
import kosh.ui.component.text.KeyValueRow
import kosh.ui.component.text.TextBytes
import kosh.ui.component.text.TextDate
import kosh.ui.component.text.TextHeader
import kosh.ui.component.text.TextLine
import kosh.ui.component.text.TextNumber
import kosh.ui.navigation.routes.RootRoute
import kosh.ui.transaction.calls.ApproveCard
import kosh.ui.transaction.calls.DeployCard
import kosh.ui.transaction.calls.FallbackCard
import kosh.ui.transaction.calls.NativeTransferCard
import kosh.ui.transaction.calls.TransferCard
import kotlinx.datetime.Instant

@Composable
fun TransactionScreen(
    id: TransactionEntity.Id,
    onNavigateUp: () -> Unit,
    onOpen: (RootRoute) -> Unit,
    onDelete: (TransactionEntity.Id) -> Unit,
) {
    val transaction = rememberTransaction(id)

    KoshScaffold(
        title = { DappTitle(transaction.entity?.dapp) },
        onNavigateUp = onNavigateUp,

        actions = {
            DappIcon(transaction.entity?.dapp)

            AdaptiveMoreMenu { dismiss ->
                when (val tx = transaction.entity) {
                    is TransactionEntity.Eip1559 -> {
                        val openExplorer = rememberOpenExplorer(tx.networkId)

                        AdaptiveMenuItem(
                            leadingIcon = {
                                Icon(
                                    Icons.AutoMirrored.Filled.OpenInNew,
                                    "Open in explorer"
                                )
                            },
                            onClick = { dismiss { openExplorer.openTransaction(tx.hash) } },
                        ) {
                            Text("Open In Explorer")
                        }
                    }

                    else -> Unit
                }

                AdaptiveMenuItem(
                    leadingIcon = { Icon(Icons.Filled.Delete, "Delete") },
                    onClick = { dismiss { onDelete(id) } }.single(),
                ) {
                    Text("Delete")
                }
            }

            Spacer(Modifier.width(8.dp))
        }
    ) { paddingValues ->

        TransactionContent(
            transaction = transaction.entity,
            contentPadding = paddingValues,
            onOpen = onOpen
        )
    }
}

@Composable
fun TransactionContent(
    transaction: TransactionEntity?,
    onOpen: (RootRoute) -> Unit,
    contentPadding: PaddingValues = PaddingValues(),
) {
    Box(Modifier.padding(contentPadding)) {
        when (transaction) {
            is TransactionEntity.PersonalMessage -> PersonalMessageContent(
                personalMessage = transaction,
            )

            is TransactionEntity.Eip712 -> TypedMessageContent(
                typedMessage = transaction,
            )

            is TransactionEntity.Eip1559 -> TransactionContent(
                transaction = transaction,
                onOpen = onOpen,
            )

            null -> Unit
        }
    }
}

@Composable
fun PersonalMessageContent(
    personalMessage: TransactionEntity.PersonalMessage,
) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val account = rememberAccount(personalMessage.sender)

        AccountItem(account.entity)

        val message = personalMessage.message.resolve(EthMessage.serializer())

        PersonalMessageCard(message?.value)

        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            TextHeader("Details")

            KeyValueRow(
                key = { Text("Created At") },
                value = { TextDate(personalMessage.createdAt) }
            )
        }
    }
}

@Composable
fun TypedMessageContent(
    typedMessage: TransactionEntity.Eip712,
) {
    val jsonText = typedMessage.jsonTypeData.resolve(JsonTypedData.serializer())

    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val network = typedMessage.networkId?.let { rememberNetwork(it) }

        if (typedMessage.networkId == null || network?.entity != null) {
            NetworkItem(network?.entity)
        }

        val account = rememberAccount(typedMessage.sender)

        AccountItem(account.entity)

        TypedMessageDomainCard(
            jsonText = jsonText?.json,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        TypedMessageCard(
            jsonText = jsonText?.json,
            modifier = Modifier.padding(horizontal = 16.dp),
        )

        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            TextHeader("Details")

            KeyValueRow(
                key = { Text("Created At") },
                value = { TextDate(typedMessage.createdAt) }
            )
        }
    }
}

@Composable
fun TransactionContent(
    transaction: TransactionEntity.Eip1559,
    onOpen: (RootRoute) -> Unit,
) {
    val network = rememberNetwork(transaction.networkId)
    val account = rememberAccount(transaction.sender)
    val data = transaction.data.resolve()

    val parsed = nullable {
        rememberContractCall(
            chainId = network.entity?.chainId ?: raise(null),
            from = account.entity?.address ?: raise(null),
            to = transaction.target,
            value = transaction.value,
            data = data ?: raise(null),
        )
    }

    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
    ) {
        NetworkItem(network.entity)

        AccountItem(account.entity) {
            Row {
                Text("nonce: ")
                TextNumber(transaction.nonce)
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                when (val call = parsed?.contractCall) {
                    is ContractCall.Transfer -> TransferCard(call, onOpen)
                    is ContractCall.Approve -> ApproveCard(call, onOpen)
                    is ContractCall.NativeTransfer -> NativeTransferCard(call)
                    is ContractCall.Deploy -> DeployCard(call)
                    is ContractCall.Fallback -> FallbackCard(call)
                    null -> FallbackCard(call)
                }
            }

            NetworkFeesCard(
                transaction,
                Modifier.padding(horizontal = 16.dp)
            )

            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                TextHeader("Details")

                if (transaction.receipt != null) {
                    KeyValueRow(
                        key = { Text("Status") },
                        value = {
                            val text = if (transaction.receipt?.success == true) "Success"
                            else "Failure"
                            TextLine(text)
                        }
                    )
                }

                KeyValueRow(
                    key = { Text("Hash") },
                    value = { TextBytes(transaction.hash.value, clickable = true) }
                )

                if (transaction.receipt != null) {
                    KeyValueRow(
                        key = { Text("Confirm At") },
                        value = { TextDate(transaction.receipt?.time ?: Instant.DISTANT_PAST) }
                    )
                }

                KeyValueRow(
                    key = { Text("Created At") },
                    value = { TextDate(transaction.createdAt) }
                )
            }
        }

        Spacer(Modifier.height(64.dp))
    }
}

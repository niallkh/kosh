package kosh.ui.transaction

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kosh.domain.entities.TransactionEntity
import kosh.domain.models.wc.WcAuthentication
import kosh.domain.models.wc.WcProposal
import kosh.domain.models.wc.WcRequest
import kosh.domain.serializers.ImmutableList
import kosh.presentation.transaction.rememberFinalizeTransactions
import kosh.presentation.transaction.rememberTransactions
import kosh.presentation.wc.rememberAuthentications
import kosh.presentation.wc.rememberProposals
import kosh.presentation.wc.rememberRequests
import kosh.ui.component.illustration.Illustration
import kosh.ui.component.text.Header
import kosh.ui.failure.AppFailureMessage
import kosh.ui.resources.illustrations.ActivityEmpty
import kosh.ui.wc.WcAuthenticationItem
import kosh.ui.wc.WcProposalItem
import kosh.ui.wc.WcRequestItem

@Composable
fun ActivityScreen(
    onOpenTransaction: (TransactionEntity.Id) -> Unit,
    onOpenProposal: (WcProposal) -> Unit,
    onOpenAuth: (WcAuthentication) -> Unit,
    onOpenRequest: (WcRequest) -> Unit,
) {
    val finalizeTransactions = rememberFinalizeTransactions()

    AppFailureMessage(finalizeTransactions.failures, { false }, {})

    val transactions = rememberTransactions()
    val proposals = rememberProposals()
    val authentications = rememberAuthentications()
    val requests = rememberRequests()

    ActivityContent(
        transactions = transactions.txs,
        proposals = proposals.proposals,
        authentications = authentications.authentications,
        requests = requests.requests,
        onSelectTransaction = { onOpenTransaction(it.id) },
        onSelectRequest = { onOpenRequest(it) },
        onSelectProposal = { onOpenProposal(it) },
        onSelectAuthentication = { onOpenAuth(it) },
    )
}

@Composable
fun ActivityContent(
    transactions: ImmutableList<TransactionEntity>,
    proposals: ImmutableList<WcProposal>,
    authentications: ImmutableList<WcAuthentication>,
    requests: ImmutableList<WcRequest>,
    onSelectTransaction: (TransactionEntity) -> Unit,
    onSelectProposal: (WcProposal) -> Unit,
    onSelectAuthentication: (WcAuthentication) -> Unit,
    onSelectRequest: (WcRequest) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 8.dp),
    ) {

        if (transactions.isEmpty() &&
            proposals.isEmpty() &&
            authentications.isEmpty() &&
            requests.isEmpty()
        ) {
            item {
                Illustration(
                    ActivityEmpty(),
                    "ActivityEmpty",
                    Modifier
                        .fillMaxWidth()
                        .padding(64.dp),
                ) {
                    Text(
                        "Get started by creating new transaction",
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        activity(
            transactions = transactions,
            proposals = proposals,
            authentications = authentications,
            requests = requests,
            onSelectTransaction = onSelectTransaction,
            onSelectProposal = onSelectProposal,
            onSelectAuthentication = onSelectAuthentication,
            onSelectRequest = onSelectRequest
        )

        item {
            Spacer(Modifier.height(64.dp))
        }
    }
}

private fun LazyListScope.activity(
    transactions: ImmutableList<TransactionEntity>,
    proposals: ImmutableList<WcProposal>,
    authentications: ImmutableList<WcAuthentication>,
    requests: ImmutableList<WcRequest>,
    onSelectTransaction: (TransactionEntity) -> Unit,
    onSelectProposal: (WcProposal) -> Unit,
    onSelectAuthentication: (WcAuthentication) -> Unit,
    onSelectRequest: (WcRequest) -> Unit,
) {
    proposals(proposals, onSelectProposal)

    authentications(authentications, onSelectAuthentication)

    requests(requests, onSelectRequest)

    transactions(transactions, onSelectTransaction)
}

private fun LazyListScope.requests(
    requests: ImmutableList<WcRequest>,
    onSelect: (WcRequest) -> Unit,
) {
    if (requests.isNotEmpty()) {
        stickyHeader {
            Header("Requests")
        }
    }

    items(
        items = requests,
        key = { it.id.value },
    ) { request ->
        WcRequestItem(
            modifier = Modifier.animateItemPlacement(),
            request = request,
            onSelect = onSelect
        )
    }
}

private fun LazyListScope.proposals(
    proposals: ImmutableList<WcProposal>,
    onSelect: (WcProposal) -> Unit,
) {
    if (proposals.isNotEmpty()) {
        stickyHeader {
            Header("Proposals")
        }
    }

    items(
        items = proposals,
        key = { it.requestId },
    ) { proposal ->
        WcProposalItem(
            modifier = Modifier.animateItemPlacement(),
            proposal = proposal,
            onSelect = onSelect
        )
    }
}

private fun LazyListScope.authentications(
    authentications: ImmutableList<WcAuthentication>,
    onSelect: (WcAuthentication) -> Unit,
) {
    if (authentications.isNotEmpty()) {
        stickyHeader {
            Header("Authentications")
        }
    }

    items(
        items = authentications,
        key = { it.id.value },
    ) { authentication ->
        WcAuthenticationItem(
            modifier = Modifier.animateItemPlacement(),
            authentication = authentication,
            onSelect = onSelect
        )
    }
}

private fun LazyListScope.transactions(
    transactions: ImmutableList<TransactionEntity>,
    onSelect: (TransactionEntity) -> Unit,
) {
    if (transactions.isNotEmpty()) {
        stickyHeader {
            Header("Transactions")
        }
    }

    items(
        items = transactions,
        key = { it.id.value.leastSignificantBits },
        contentType = { transaction ->
            when (transaction) {
                is TransactionEntity.Eip1559 -> 1
                is TransactionEntity.Eip712 -> 2
                is TransactionEntity.PersonalMessage -> 3
            }
        }
    ) { transaction ->
        when (transaction) {
            is TransactionEntity.Eip1559 -> TransactionItem(
                modifier = Modifier.animateItemPlacement(),
                transaction = transaction,
                onClick = { onSelect(transaction) },
            )

            is TransactionEntity.Eip712 -> TypedMessageItem(
                modifier = Modifier.animateItemPlacement(),
                typedMessage = transaction,
                onClick = { onSelect(transaction) },
            )

            is TransactionEntity.PersonalMessage -> PersonalMessageItem(
                modifier = Modifier.animateItemPlacement(),
                personalMessage = transaction,
                onClick = { onSelect(transaction) },
            )
        }
    }
}


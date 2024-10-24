package kosh.ui.transaction

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kosh.domain.entities.TransactionEntity
import kosh.domain.models.reown.WcAuthentication
import kosh.domain.models.reown.WcRequest
import kosh.domain.models.reown.WcSessionProposal
import kosh.domain.serializers.ImmutableList
import kosh.presentation.reown.AuthenticationsState
import kosh.presentation.reown.ProposalsState
import kosh.presentation.reown.RequestsState
import kosh.presentation.reown.rememberAuthentications
import kosh.presentation.reown.rememberProposals
import kosh.presentation.reown.rememberRequests
import kosh.presentation.transaction.TransactionsState
import kosh.presentation.transaction.rememberFinalizeTransactions
import kosh.presentation.transaction.rememberTransactions
import kosh.ui.component.LoadingIndicator
import kosh.ui.component.illustration.Illustration
import kosh.ui.component.refresh.DragToRefreshBox
import kosh.ui.component.text.Header
import kosh.ui.failure.AppFailureMessage
import kosh.ui.reown.WcAuthenticationItem
import kosh.ui.reown.WcProposalItem
import kosh.ui.reown.WcRequestItem
import kosh.ui.resources.illustrations.ActivityEmpty
import kotlinx.collections.immutable.toPersistentList

@Composable
fun ActivityScreen(
    paddingValues: PaddingValues,
    scrollBehavior: TopAppBarScrollBehavior,
    onOpenTransaction: (TransactionEntity.Id) -> Unit,
    onOpenProposal: (WcSessionProposal) -> Unit,
    onOpenAuth: (WcAuthentication) -> Unit,
    onOpenRequest: (WcRequest) -> Unit,
) {
    val finalizeTransactions = rememberFinalizeTransactions()
    val transactions = rememberTransactions()

    AppFailureMessage(finalizeTransactions.failures)

    DragToRefreshBox(
        isRefreshing = finalizeTransactions.refreshing,
        enabled = transactions.init,
        onRefresh = { finalizeTransactions.refresh() },
        scrollBehavior = scrollBehavior,
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        ActivityContent(
            isRefreshing = finalizeTransactions.refreshing,
            transactions = transactions,
            onSelectTransaction = { onOpenTransaction(it.id) },
            onSelectRequest = { onOpenRequest(it) },
            onSelectProposal = { onOpenProposal(it) },
            onSelectAuthentication = { onOpenAuth(it) },
        )
    }

    LoadingIndicator(
        finalizeTransactions.loading && !finalizeTransactions.refreshing,
        Modifier.padding(paddingValues),
    )
}

@Composable
fun ActivityContent(
    isRefreshing: Boolean = false,
    transactions: TransactionsState = rememberTransactions(),
    proposals: ProposalsState = rememberProposals(),
    authentications: AuthenticationsState = rememberAuthentications(),
    requests: RequestsState = rememberRequests(),
    onSelectTransaction: (TransactionEntity) -> Unit,
    onSelectProposal: (WcSessionProposal) -> Unit,
    onSelectAuthentication: (WcAuthentication) -> Unit,
    onSelectRequest: (WcRequest) -> Unit,
) {
    LazyColumn(
        Modifier.fillMaxSize(),
        userScrollEnabled = !isRefreshing
    ) {

        when {
            !transactions.init -> {
                transactions(List(7) { null }.toPersistentList(), onSelectTransaction)
            }

            transactions.transactions.isEmpty() &&
                    proposals.proposals.isEmpty() &&
                    authentications.authentications.isEmpty() &&
                    requests.requests.isEmpty() -> item {
                EmptyActivityContent(Modifier.animateItem())
            }

            else -> {
                proposals(proposals.proposals, onSelectProposal)

                authentications(authentications.authentications, onSelectAuthentication)

                requests(requests.requests, onSelectRequest)

                transactions(transactions.transactions, onSelectTransaction)
            }
        }

        item {
            Spacer(Modifier.height(64.dp))
        }
    }
}

@Composable
private fun EmptyActivityContent(
    modifier: Modifier = Modifier,
) {
    Illustration(
        ActivityEmpty(),
        "ActivityEmpty",
        modifier
            .fillMaxWidth()
            .padding(64.dp),
    ) {
        Text(
            "Get started by sending your first transaction",
            textAlign = TextAlign.Center
        )
    }
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
            modifier = Modifier.animateItem(),
            request = request,
            onSelect = onSelect
        )
    }
}

private fun LazyListScope.proposals(
    proposals: ImmutableList<WcSessionProposal>,
    onSelect: (WcSessionProposal) -> Unit,
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
            modifier = Modifier.animateItem(),
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
            modifier = Modifier.animateItem(),
            authentication = authentication,
            onSelect = onSelect
        )
    }
}

private fun LazyListScope.transactions(
    transactions: ImmutableList<TransactionEntity?>,
    onSelect: (TransactionEntity) -> Unit,
) {
    if (transactions.isNotEmpty()) {
        stickyHeader {
            Header("Transactions")
        }
    }

    items(
        count = transactions.size,
        key = { transactions[it]?.id?.value?.leastSignificantBits ?: it },
        contentType = {
            when (transactions[it]) {
                null -> 0
                is TransactionEntity.Eip1559 -> 1
                is TransactionEntity.Eip712 -> 2
                is TransactionEntity.PersonalMessage -> 3
            }
        }
    ) {
        when (val transaction = transactions[it]) {
            is TransactionEntity.Eip1559 -> TransactionItem(
                modifier = Modifier.animateItem(),
                transaction = transaction,
                onClick = { onSelect(transaction) },
            )

            is TransactionEntity.Eip712 -> TypedMessageItem(
                modifier = Modifier.animateItem(),
                typedMessage = transaction,
                onClick = { onSelect(transaction) },
            )

            is TransactionEntity.PersonalMessage -> PersonalMessageItem(
                modifier = Modifier.animateItem(),
                personalMessage = transaction,
                onClick = { onSelect(transaction) },
            )

            null -> PersonalMessageItem(
                modifier = Modifier.animateItem(),
                personalMessage = transaction,
                onClick = { },
            )
        }
    }
}

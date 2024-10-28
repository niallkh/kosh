package kosh.ui.transaction.calls

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kosh.domain.models.web3.ContractCall
import kosh.ui.component.items.AddressItem
import kosh.ui.component.text.TextHeader
import kosh.ui.navigation.routes.RootRoute

@Composable
fun ApproveCard(
    approve: ContractCall.Approve,
    onOpen: (RootRoute) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextHeader("Approve")

        OutlinedCard {
            TokenAmountItem(
                approve.chainId,
                approve.token,
                approve.approved,
                approve.tokenId,
                onOpen,
            )

            AddressItem(approve.spender) {
                Text("Spender")
            }
        }
    }
}

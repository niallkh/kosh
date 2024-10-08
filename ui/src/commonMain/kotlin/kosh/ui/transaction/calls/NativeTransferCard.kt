package kosh.ui.transaction.calls

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kosh.domain.models.web3.ContractCall
import kosh.ui.component.text.TextHeader

@Composable
fun NativeTransferCard(
    transfer: ContractCall.NativeTransfer,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextHeader("Transfer")

        OutlinedCard {
            TokenAmountItem(transfer.token, transfer.amount) {
                Text("Amount")
            }

            AccountItem(transfer.destination) {
                Text("Destination")
            }
        }
    }
}

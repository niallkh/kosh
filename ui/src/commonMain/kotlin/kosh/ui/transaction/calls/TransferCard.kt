package kosh.ui.transaction.calls

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kosh.domain.models.web3.ContractCall
import kosh.ui.component.items.AddressItem
import kosh.ui.component.text.TextHeader

@Composable
fun TransferCard(
    transfer: ContractCall.Transfer,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextHeader("Transfer")

        OutlinedCard {
            if (transfer.tokenIds.isEmpty()) {
                TokenAmountItem(transfer.chainId, transfer.token, transfer.amounts.first())
            } else {
                transfer.tokenIds.zip(transfer.amounts).forEachIndexed { index, (tokenId, amount) ->
                    key(index) {
                        TokenAmountItem(transfer.chainId, transfer.token, amount, tokenId)
                    }
                }
            }

            if (transfer.from != transfer.sender) {
                AddressItem(transfer.from) {
                    Text("From")
                }
            }

            AddressItem(transfer.destination) {
                Text("Destination")
            }
        }
    }
}

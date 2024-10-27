package kosh.ui.transaction.calls

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kosh.domain.models.web3.ContractCall
import kosh.domain.utils.orEmpty
import kosh.presentation.transaction.rememberFunctionName
import kosh.ui.component.items.AddressItem
import kosh.ui.component.placeholder.placeholder
import kosh.ui.component.text.KeyValueColumn
import kosh.ui.component.text.TextBytes
import kosh.ui.component.text.TextHeader

@Composable
fun FallbackCard(
    call: ContractCall.Fallback?,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val functionName = call?.selector?.let { rememberFunctionName(it) }

        TextHeader(
            functionName?.functionName?.value?.replaceFirstChar { it.uppercaseChar() }
                ?: "Contract Call"
        )

        OutlinedCard {
            TokenAmountItem(
                call?.token,
                call?.value,
            ) {
                Text("Value")
            }

            AddressItem(call?.contract) {
                Text("Contract Address")
            }

            KeyValueColumn(
                key = { Text("Data") },
                value = {
                    TextBytes(
                        call?.input.orEmpty(),
                        Modifier.placeholder(call == null)
                    )
                },
                Modifier.padding(horizontal = 16.dp),
            )

            Spacer(Modifier.height(8.dp))
        }
    }
}

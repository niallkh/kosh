package kosh.ui.transaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kosh.domain.models.Address
import kosh.domain.models.ChainId
import kosh.domain.models.orZero
import kosh.eth.abi.json.JsonEip712
import kosh.ui.component.placeholder.placeholder
import kosh.ui.component.text.KeyValueRow
import kosh.ui.component.text.TextAddressShort
import kosh.ui.component.text.TextBytes
import kosh.ui.component.text.TextChainId
import kosh.ui.component.text.TextHeader
import kosh.ui.component.text.TextLine
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject

private val json = Json { prettyPrint = true }

@Composable
fun TypedMessageCard(
    jsonText: String? = null,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        TextHeader("Message")

        OutlinedCard(
            modifier = Modifier
        ) {

            Text(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .placeholder(jsonText == null),
                text = remember(jsonText) {
                    jsonText?.let {
                        val decoded = json.decodeFromString<JsonElement>(it)
                        json.encodeToString(decoded.jsonObject["message"])
                    } ?: "Unknown Typed Message. "
                },
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Composable
fun TypedMessageDomainCard(
    jsonText: String? = null,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        TextHeader("Domain")

        val domain by produceState<JsonEip712.Domain?>(null, jsonText) {
            value = jsonText?.let {
                JsonEip712.from(it).domain
            }
        }

        if (domain == null || domain?.name != null) {
            KeyValueRow(
                modifier = Modifier.fillMaxWidth(),
                key = { Text("Name") },
                value = {
                    TextLine(
                        domain?.name ?: "Unknown name",
                        Modifier.placeholder(domain == null)
                    )
                }
            )
        }

        if (domain == null || domain?.version != null) {
            KeyValueRow(
                modifier = Modifier.fillMaxWidth(),
                key = { Text("Version") },
                value = {
                    TextLine(
                        domain?.version ?: "0.0.0",
                        Modifier.placeholder(domain == null)
                    )
                }
            )
        }

        if (domain == null || domain?.chainId != null) {
            KeyValueRow(
                modifier = Modifier.fillMaxWidth(),
                key = { Text("Chain Id") },
                value = {
                    TextChainId(
                        domain?.chainId?.let { ChainId(it) }.orZero(),
                        Modifier.placeholder(domain == null)
                    )
                }
            )
        }

        if (domain == null || domain?.verifyingContract != null) {
            KeyValueRow(
                modifier = Modifier.fillMaxWidth(),
                key = { Text("Verifying Contract") },
                value = {
                    TextAddressShort(
                        domain?.verifyingContract ?: Address().toString(),
                        Modifier.placeholder(domain == null)
                    )
                }
            )
        }

        if (domain == null || domain?.salt != null) {
            KeyValueRow(
                modifier = Modifier.fillMaxWidth(),
                key = { Text("Salt") },
                value = {
                    TextBytes(
                        domain?.salt.orEmpty(),
                        Modifier.placeholder(domain == null)
                    )
                }
            )
        }
    }
}

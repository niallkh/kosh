package kosh.ui.reown

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowOutward
import androidx.compose.material.icons.filled.ControlPointDuplicate
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import kosh.domain.models.reown.WcRequest
import kosh.eth.abi.eip712.Eip712
import kosh.presentation.transaction.rememberContractCall
import kosh.ui.component.dapp.DappIcon
import kosh.ui.component.single.single
import kosh.ui.component.text.TextLine
import kosh.ui.resources.icons.Networks
import kosh.ui.resources.icons.PersonalSignature
import kosh.ui.resources.icons.TypedSignature
import kosh.ui.transaction.calls.TextFunctionName

@Composable
fun WcRequestItem(
    request: WcRequest,
    onSelect: (WcRequest) -> Unit,
    modifier: Modifier = Modifier,
) {
    ListItem(
        modifier = modifier.clickable(onClick = { onSelect(request) }.single()),
        leadingContent = {
            DappIcon(request.dapp)
        },
        headlineContent = {
            TextLine(request.dapp.name)
        },
        supportingContent = {
            when (val call = request.call) {
                is WcRequest.Call.SendTransaction -> {
                    val contractCall = rememberContractCall(
                        call.chainId, call.from, call.to, call.value, call.data
                    )

                    TextFunctionName(contractCall.contractCall)
                }

                is WcRequest.Call.SignPersonal -> {
                    TextLine(call.message.value)
                }

                is WcRequest.Call.SignTyped -> {
                    val domain by produceState<Eip712.Domain?>(null, call.json.json) {
                        value = call.json.json.let {
                            Eip712.fromJson(it).domain
                        }
                    }

                    TextLine(domain?.name?.value ?: "Unknown Typed Message")
                }

                is WcRequest.Call.AddNetwork -> {
                    TextLine("Add Network: ${call.chainName}")
                }

                is WcRequest.Call.WatchAsset -> {
                    TextLine("Add Asset: ${call.address}")
                }
            }
        },
        trailingContent = {
            when (request.call) {
                is WcRequest.Call.SendTransaction ->
                    Icon(Icons.Filled.ArrowOutward, "SendTransaction")

                is WcRequest.Call.SignPersonal ->
                    Icon(PersonalSignature, "PersonalSignature")

                is WcRequest.Call.SignTyped ->
                    Icon(TypedSignature, "TypedSignature")

                is WcRequest.Call.AddNetwork ->
                    Icon(Networks, "AddNetwork")

                is WcRequest.Call.WatchAsset ->
                    Icon(Icons.Filled.ControlPointDuplicate, "AddAsset")
            }
        }
    )
}

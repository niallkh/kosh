package kosh.ui.transaction.calls

import androidx.compose.runtime.Composable
import kosh.domain.models.web3.ContractCall
import kosh.presentation.transaction.rememberFunctionName
import kosh.ui.component.text.TextLine

@Composable
fun TextFunctionName(contractCall: ContractCall?) {
    if (contractCall?.selector != null) {
        val functionName = contractCall.selector?.let { rememberFunctionName(it) }
        TextLine(functionName?.functionName?.value?.replaceFirstChar { it.uppercaseChar() })
    } else {
        val text = when (contractCall) {
            is ContractCall.Deploy -> "Deploy"
            is ContractCall.NativeTransfer -> "Transfer"
            else -> "Contract Call"
        }
        TextLine(text)
    }
}

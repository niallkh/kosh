package kosh.ui.transaction.calls

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kosh.domain.models.web3.ContractCall
import kosh.presentation.transaction.rememberFunctionName
import kosh.ui.component.placeholder.placeholder
import kosh.ui.component.text.TextLine

@Composable
fun TextFunctionName(contractCall: ContractCall?) {
    if (contractCall?.selector != null) {
        val functionName = contractCall.selector?.let { rememberFunctionName(it) }
        TextLine(
            functionName?.functionName?.value ?: "Contract Call",
            Modifier.placeholder(functionName == null)
        )
    } else {
        val text = when (contractCall) {
            is ContractCall.Deploy -> "Deploy"
            is ContractCall.NativeTransfer -> "Transfer"
            else -> "Contract Call"
        }
        TextLine(text)
    }
}

package kosh.presentation.transaction

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import kosh.domain.failure.Web3Failure
import kosh.domain.models.FunSelector
import kosh.domain.repositories.FunctionSignatureRepo
import kosh.presentation.Load
import kosh.presentation.core.di
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Composable
fun rememberFunctionName(
    funSelector: FunSelector,
    functionSignatureRepo: FunctionSignatureRepo = di { appRepositoriesComponent.functionSignatureRepo },
): FunctionNameState {
    val functionName = Load(funSelector) {
        functionSignatureRepo.get(funSelector).bind()
            ?.let { FunctionName(it.substringBefore("(")) }
            ?: FunctionName(funSelector.toString())
    }

    return FunctionNameState(
        functionName = functionName.content,
        loading = functionName.loading,
        failure = functionName.failure,
        retry = { functionName.retry() }
    )
}

@Immutable
data class FunctionNameState(
    val functionName: FunctionName?,
    val loading: Boolean,
    val failure: Web3Failure?,
    val retry: () -> Unit,
)

@Immutable
@Serializable
@JvmInline
value class FunctionName(val value: String)

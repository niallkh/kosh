package kosh.presentation.network

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import arrow.core.raise.recover
import arrow.core.raise.withError
import kosh.domain.entities.NetworkEntity
import kosh.domain.failure.NetworkFailure
import kosh.domain.usecases.network.NetworkService
import kosh.domain.usecases.network.chainIdValidator
import kosh.domain.usecases.network.explorerValidator
import kosh.domain.usecases.network.iconValidator
import kosh.domain.usecases.network.networkNameValidator
import kosh.domain.usecases.network.rpcProviderValidator
import kosh.domain.usecases.network.writeProviderValidator
import kosh.domain.usecases.token.tokenNameValidator
import kosh.domain.usecases.token.tokenSymbolValidator
import kosh.presentation.component.textfield.TextFieldState
import kosh.presentation.component.textfield.rememberTextField
import kosh.presentation.di.di
import kosh.presentation.token.rememberNativeToken

@Composable
fun rememberNetworkForm(
    id: NetworkEntity.Id?,
    networkService: NetworkService = di { domain.networkService },
): NetworkFormState {
    val network = id?.let { rememberNetwork(it) }
    val token = network?.entity?.chainId?.let { rememberNativeToken(it) }
    val networkNameTextField = rememberTextField(network?.entity?.name) {
        networkNameValidator(it)
    }
    val tokenIconTextField = rememberTextField(token?.entity?.icon?.toString()) {
        iconValidator(it)
    }
    val networkIconTextField = rememberTextField(network?.entity?.icon?.toString()) {
        iconValidator(it)
    }
    val explorer = network?.entity?.explorers?.firstOrNull()
    val explorerTextField = rememberTextField(explorer?.toString()) {
        explorerValidator(it)
    }
    val tokenSymbolTextField = rememberTextField(token?.entity?.symbol) {
        tokenSymbolValidator(it)
    }
    val tokenNameTextField = rememberTextField(token?.entity?.name) {
        tokenNameValidator(it)
    }
    val chainIdTextField = rememberTextField(network?.entity?.chainId?.value?.toString()) {
        chainIdValidator(it)
    }
    val writeRpcTextField = rememberTextField(network?.entity?.writeRpcProvider?.toString()) {
        writeProviderValidator(it)
    }
    val readRpcTextField = rememberTextField(network?.entity?.readRpcProvider?.toString()) {
        rpcProviderValidator(it)
    }

    var loading by remember { mutableStateOf(false) }
    var failure by remember { mutableStateOf<NetworkFailure?>(null) }
    var retry by remember { mutableIntStateOf(0) }
    var save by remember { mutableStateOf(false) }
    var saved by remember { mutableStateOf(false) }

    LaunchedEffect(id, retry, save) {
        if (!save) return@LaunchedEffect

        loading = true

        recover({
            val either = withError({ null }) {
                if (id == null) {
                    networkService.add(
                        chainId = chainIdTextField.validate().bind(),
                        name = networkNameTextField.validate().bind(),
                        readRpcProvider = readRpcTextField.validate().bind(),
                        writeRpcProvider = writeRpcTextField.validate().bind(),
                        explorers = explorerTextField.validate().bind().let { listOfNotNull(it) },
                        icon = networkIconTextField.validate().bind(),
                        tokenName = tokenNameTextField.validate().bind(),
                        tokenSymbol = tokenSymbolTextField.validate().bind(),
                        tokenIcon = tokenIconTextField.validate().bind()
                    )
                } else {
                    networkService.update(
                        id = id,
                        name = networkNameTextField.validate().bind(),
                        readRpcProvider = readRpcTextField.validate().bind(),
                        writeRpcProvider = writeRpcTextField.validate().bind(),
                        explorers = explorerTextField.validate().bind().let { listOfNotNull(it) },
                        icon = networkIconTextField.validate().bind(),
                        tokenName = tokenNameTextField.validate().bind(),
                        tokenSymbol = tokenSymbolTextField.validate().bind(),
                        tokenIcon = tokenIconTextField.validate().bind()
                    )
                }
            }

            either.bind()
            saved = true

            loading = false
            save = false
            failure = null
        }) {
            loading = false
            failure = it
        }
    }

    return NetworkFormState(
        networkNameTextField = networkNameTextField,
        readRpcTextField = readRpcTextField,
        writeRpcTextField = writeRpcTextField,
        tokenNameTextField = tokenNameTextField,
        tokenSymbolTextField = tokenSymbolTextField,
        chainIdTextField = chainIdTextField,
        tokenIconTextField = tokenIconTextField,
        networkIconTextField = networkIconTextField,
        explorerTextField = explorerTextField,
        loading = loading,
        failure = failure,
        saved = saved,
        save = {
            retry++
            save = true
        },
        retry = { retry++ },
    )
}

@Immutable
data class NetworkFormState(
    val networkNameTextField: TextFieldState<*, *>,
    val readRpcTextField: TextFieldState<*, *>,
    val writeRpcTextField: TextFieldState<*, *>,
    val chainIdTextField: TextFieldState<*, *>,
    val explorerTextField: TextFieldState<*, *>,
    val networkIconTextField: TextFieldState<*, *>,
    val tokenNameTextField: TextFieldState<*, *>,
    val tokenSymbolTextField: TextFieldState<*, *>,
    val tokenIconTextField: TextFieldState<*, *>,
    val saved: Boolean,
    val loading: Boolean,
    val failure: NetworkFailure?,
    val save: () -> Unit,
    val retry: () -> Unit,
)

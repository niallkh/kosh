package kosh.ui.token

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kosh.domain.models.ChainAddress
import kosh.domain.models.token.NftMetadata
import kosh.domain.models.token.TokenMetadata
import kosh.domain.serializers.BigInteger
import kosh.presentation.component.textfield.TextFieldState
import kosh.presentation.component.textfield.rememberTextField
import kosh.presentation.network.rememberNetwork
import kosh.presentation.token.CreateNftState
import kosh.presentation.token.SearchNftState
import kosh.presentation.token.rememberCreateNft
import kosh.presentation.token.rememberSearchNft
import kosh.ui.component.LoadingIndicator
import kosh.ui.component.cards.NftCard
import kosh.ui.component.scaffold.LocalSnackbarHostState
import kosh.ui.component.search.SearchView
import kosh.ui.component.textfield.PasteClearIcon
import kosh.ui.failure.AppFailureItem
import kosh.ui.failure.AppFailureMessage

@Composable
fun SearchNftScreen(
    chainAddress: ChainAddress,
    tokenId: BigInteger?,
    onNavigateUp: () -> Unit,
    onFinish: () -> Unit,
    query: TextFieldState = rememberTextField(tokenId?.toString()),
    searchNft: SearchNftState = rememberSearchNft(chainAddress, query.value.text),
    createNft: CreateNftState = rememberCreateNft(onCreated = { onFinish() }),
) {
    val snackbarHostState = LocalSnackbarHostState.current

    Scaffold(
        topBar = {
            SearchView(
                modifier = Modifier
                    .windowInsetsPadding(TopAppBarDefaults.windowInsets),
                value = query.value,
                onValueChange = query::onChange,
                onUp = onNavigateUp,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                ),
                placeholder = "Enter Token Id",
                trailingIcon = {
                    PasteClearIcon(
                        textField = query.value,
                        onChange = query::onChange
                    )
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
                .imePadding()
        ) {

            AppFailureMessage(createNft.failure)

            searchNft.failure?.let {
                AppFailureItem(it) {
                    searchNft.retry()
                }
            } ?: run {
                SearchNftContent(
                    modifier = Modifier.padding(16.dp),
                    nft = searchNft.nft,
                    token = searchNft.token,
                ) { nft ->
                    searchNft.token?.let { token ->
                        createNft.create(token, nft)
                    }
                }
            }

            val loading by remember {
                derivedStateOf { searchNft.loading || createNft.loading }
            }

            LoadingIndicator(loading)
        }
    }
}

@Composable
private fun SearchNftContent(
    token: TokenMetadata?,
    nft: NftMetadata?,
    modifier: Modifier = Modifier,
    onSelect: (NftMetadata) -> Unit,
) {
    if (nft != null) {
        val network = token?.chainId?.let { rememberNetwork(it) }

        NftCard(
            modifier = modifier,
            token = token,
            nft = nft,
            maxSize = 1024,
            network = network?.entity,
            onClick = { onSelect(nft) },
        )
    } else {
        Box(
            modifier = modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "Not Found",
            )
        }
    }
}

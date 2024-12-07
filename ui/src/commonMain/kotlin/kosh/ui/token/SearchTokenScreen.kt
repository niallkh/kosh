package kosh.ui.token

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kosh.domain.models.Address
import kosh.domain.models.ChainAddress
import kosh.domain.models.caip10
import kosh.domain.models.eip55
import kosh.domain.models.token.TokenMetadata
import kosh.domain.models.token.isNft
import kosh.domain.serializers.ImmutableList
import kosh.presentation.component.textfield.TextFieldState
import kosh.presentation.component.textfield.rememberTextField
import kosh.presentation.token.CreateTokenState
import kosh.presentation.token.SearchTokenState
import kosh.presentation.token.rememberCreateToken
import kosh.presentation.token.rememberSearchToken
import kosh.ui.component.LoadingIndicator
import kosh.ui.component.items.TokenItem
import kosh.ui.component.scaffold.LocalSnackbarHostState
import kosh.ui.component.search.SearchView
import kosh.ui.component.text.TextHeader
import kosh.ui.component.text.TextLine
import kosh.ui.component.textfield.PasteClearIcon
import kosh.ui.failure.AppFailureItem
import kosh.ui.failure.AppFailureMessage

@Composable
fun SearchTokenScreen(
    address: Address?,
    onFinish: () -> Unit,
    onNavigateUp: () -> Unit,
    onNft: (ChainAddress) -> Unit,
    query: TextFieldState = rememberTextField(address?.eip55()),
    searchToken: SearchTokenState = rememberSearchToken(query.value.text),
    createToken: CreateTokenState = rememberCreateToken(onCreated = { onFinish() }),
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
                placeholder = "Enter Token Name or Address",
                trailingIcon = {
                    PasteClearIcon(
                        textField = query.value,
                        onChange = query::onChange
                    )
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->

        Box(
            modifier = Modifier.padding(innerPadding)
                .imePadding()
        ) {

            AppFailureMessage(createToken.failure)

            searchToken.failure?.let {
                AppFailureItem(it) {
                    searchToken.retry()
                }
            } ?: run {
                SearchTokenContent(
                    tokens = searchToken.tokens,
                    onSelect = {
                        if (it.isNft) {
                            onNft(ChainAddress(it.chainId, it.address))
                        } else {
                            createToken.create(it)
                        }
                    }
                )
            }

            val loading by remember {
                derivedStateOf { searchToken.loading || createToken.loading }
            }

            LoadingIndicator(loading)
        }
    }

}

@Composable
private fun SearchTokenContent(
    tokens: ImmutableList<TokenMetadata>,
    onSelect: (TokenMetadata) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        if (tokens.isNotEmpty()) {
            stickyHeader {
                Surface(Modifier.fillMaxWidth()) {
                    TextHeader(
                        "Results",
                        Modifier.padding(
                            horizontal = 16.dp,
                            vertical = 8.dp
                        )
                    )
                }
            }

            items(
                items = tokens,
                key = { ChainAddress(it.chainId, it.address).caip10() }
            ) { token ->
                TokenItem(
                    token = token,
                    onClick = { onSelect(token) },
                    trailingContent = { TextLine(token.symbol) },
                )
            }
        } else {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "Not Found",
                    )
                }
            }
        }

        item {
            Spacer(Modifier.height(8.dp))
        }
    }
}

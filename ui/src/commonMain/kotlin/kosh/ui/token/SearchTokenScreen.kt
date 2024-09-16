package kosh.ui.token

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import kosh.domain.entities.TokenEntity
import kosh.domain.models.Address
import kosh.domain.models.ChainAddress
import kosh.domain.models.caip10
import kosh.domain.models.eip55
import kosh.domain.models.token.TokenMetadata
import kosh.domain.models.token.isNft
import kosh.domain.serializers.ImmutableList
import kosh.presentation.component.textfield.rememberTextField
import kosh.presentation.network.rememberNetwork
import kosh.presentation.token.SearchTokenState
import kosh.presentation.token.rememberCreateToken
import kosh.presentation.token.rememberSearchToken
import kosh.ui.component.LoadingIndicator
import kosh.ui.component.icon.ChainBadge
import kosh.ui.component.icon.TokenIcon
import kosh.ui.component.search.SearchView
import kosh.ui.component.text.TextHeader
import kosh.ui.component.text.TextLine
import kosh.ui.component.textfield.PasteClearIcon
import kosh.ui.failure.AppFailureItem
import kosh.ui.failure.AppFailureMessage

@Composable
fun SearchTokenScreen(
    address: Address?,
    query: Pair<TextFieldValue, (TextFieldValue) -> Unit> = rememberTextField(address?.eip55()),
    searchToken: SearchTokenState = rememberSearchToken(query.first.text),
    onResult: (TokenEntity.Id) -> Unit,
    onNft: (ChainAddress) -> Unit,
    onNavigateUp: () -> Unit,
) {
    CompositionLocalProvider(LocalAbsoluteTonalElevation provides 3.dp) {

        Scaffold(
            topBar = {
                SearchView(
                    modifier = Modifier
                        .windowInsetsPadding(TopAppBarDefaults.windowInsets),
                    value = query.first,
                    onValueChange = query.second,
                    onUp = onNavigateUp,
                    placeholder = "Enter Token Name or Address",
                    trailingIcon = {
                        PasteClearIcon(
                            textField = query.first,
                            onChange = query.second
                        )
                    }
                )
            },
            containerColor = MaterialTheme.colorScheme.surface,
        ) { innerPadding ->

            Box(
                modifier = Modifier.padding(innerPadding)
                    .consumeWindowInsets(innerPadding)
                    .imePadding()
            ) {

                val createToken = rememberCreateToken()

                AppFailureMessage(createToken.failure)

                LaunchedEffect(createToken.created != null) {
                    createToken.created?.let { onResult(it) }
                }

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

                LoadingIndicator(
                    searchToken.loading || createToken.loading,
                    Modifier.padding(innerPadding),
                )
            }
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
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        if (tokens.isNotEmpty()) {
            stickyHeader {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    TextHeader(
                        modifier = Modifier.padding(
                            horizontal = 16.dp,
                            vertical = 8.dp
                        ),
                        text = "Results",
                    )
                }
            }

            items(
                items = tokens,
                key = { ChainAddress(it.chainId, it.address).caip10() }
            ) { token ->
                SearchTokenItem(token) {
                    onSelect(token)
                }
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
    }
}

@Composable
fun SearchTokenItem(
    token: TokenMetadata,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val (network) = rememberNetwork(token.chainId)

    ListItem(
        modifier = modifier.clickable(onClick = onClick),
        leadingContent = {
            Box {
                TokenIcon(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape),
                    symbol = token.symbol,
                    icon = token.icon,
                )

                if (network?.chainId != null) {
                    ChainBadge(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .offset(2.dp, 2.dp),
                        chainId = network.chainId,
                        symbol = network.name,
                        icon = network.icon,
                    )
                }
            }
        },
        headlineContent = { TextLine(token.name) },
        trailingContent = { TextLine(token.symbol) },
    )
}

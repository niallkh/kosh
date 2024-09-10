package kosh.ui.token

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import kosh.domain.entities.TokenEntity
import kosh.domain.models.ChainAddress
import kosh.domain.models.token.NftMetadata
import kosh.domain.models.token.TokenMetadata
import kosh.domain.serializers.BigInteger
import kosh.presentation.component.textfield.rememberTextField
import kosh.presentation.network.rememberNetwork
import kosh.presentation.token.SearchNftState
import kosh.presentation.token.rememberCreateNft
import kosh.presentation.token.rememberSearchNft
import kosh.ui.component.LoadingIndicator
import kosh.ui.component.search.SearchView
import kosh.ui.component.textfield.PasteClearIcon
import kosh.ui.component.token.NftItem
import kosh.ui.failure.AppFailureItem
import kosh.ui.failure.AppFailureMessage

@Composable
fun SearchNftScreen(
    chainAddress: ChainAddress,
    tokenId: BigInteger?,
    query: Pair<TextFieldValue, (TextFieldValue) -> Unit> = rememberTextField(tokenId?.toString()),
    searchNft: SearchNftState = rememberSearchNft(chainAddress, query.first.text),
    onNavigateUp: () -> Unit,
    onResult: (TokenEntity.Id) -> Unit,
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
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                    ),
                    placeholder = "Enter Token Id",
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

                val createNft = rememberCreateNft()

                AppFailureMessage(createNft.failure)

                LaunchedEffect(createNft.created != null) {
                    createNft.created?.let { onResult(it) }
                }

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

                LoadingIndicator(searchNft.loading || createNft.loading)
            }
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

        NftItem(
            modifier = modifier,
            tokenName = nft.name ?: token?.name ?: "",
            tokenNameStyle = MaterialTheme.typography.bodyLarge,
            image = nft.image,
            networkIcon = network?.entity?.icon,
            chainId = network?.entity?.chainId,
            networkName = network?.entity?.name,
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

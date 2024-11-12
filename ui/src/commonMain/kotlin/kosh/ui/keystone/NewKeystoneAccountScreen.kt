package kosh.ui.keystone

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import kosh.domain.entities.AccountEntity
import kosh.domain.models.account.ethereumAddressIndex
import kosh.domain.models.hw.Keystone
import kosh.domain.models.web3.Signer
import kosh.presentation.account.rememberCreateAccount
import kosh.presentation.keystone.KeystoneAccountsState
import kosh.presentation.keystone.rememberKeystoneAccounts
import kosh.presentation.keystone.rememberKeystoneListener
import kosh.ui.component.LoadingIndicator
import kosh.ui.component.items.SignerItem
import kosh.ui.component.scaffold.KoshScaffold
import kosh.ui.failure.AppFailureItem
import kosh.ui.failure.AppFailureMessage

@Composable
fun NewKeystoneAccountScreen(
    keystone: Keystone,
    onNavigateUp: () -> Unit,
    onFinish: (AccountEntity.Id) -> Unit,
) {
    val keystoneListener = rememberKeystoneListener()
    val keystoneAccounts = rememberKeystoneAccounts(
        listener = keystoneListener.listener,
        keystone = keystone,
    )

    KoshScaffold(
        modifier = Modifier,
        title = { Text("Keystone New Account") },
        onNavigateUp = onNavigateUp
    ) { contentPadding ->

        val createAccountState = rememberCreateAccount()

        AppFailureMessage(createAccountState.failure)

        LaunchedEffect(createAccountState.createdAccount) {
            createAccountState.createdAccount?.let(onFinish)
        }

        NewKeystoneAccountContent(
            keystoneAccounts = keystoneAccounts,
            paddingValues = contentPadding,
            onSelect = { createAccountState.create(it) },
        )

        LoadingIndicator(
            createAccountState.loading || keystoneAccounts.loading,
            Modifier.padding(contentPadding),
        )
    }
}

@Composable
fun NewKeystoneAccountContent(
    keystoneAccounts: KeystoneAccountsState,
    paddingValues: PaddingValues = PaddingValues(),
    onSelect: (Signer) -> Unit,
) {
    LazyColumn(
        contentPadding = paddingValues
    ) {
        keystoneAccounts.failure?.let {
            item {
                AppFailureItem(it) {
                    keystoneAccounts.retry()
                }
            }
        } ?: run {
            items(
                items = keystoneAccounts.accounts,
                key = { signer -> signer.derivationPath.ethereumAddressIndex.toInt() }
            ) { signer ->
                SignerItem(signer) {
                    onSelect(signer)
                }
            }
        }
    }
}

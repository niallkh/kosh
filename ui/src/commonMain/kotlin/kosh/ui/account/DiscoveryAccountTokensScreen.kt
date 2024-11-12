package kosh.ui.account

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kosh.domain.entities.AccountEntity
import kosh.presentation.account.rememberDiscoveryAccountTokens
import kosh.ui.component.LoadingIndicator
import kosh.ui.component.scaffold.KoshScaffold
import kosh.ui.failure.AppFailureItem

@Composable
fun DiscoveryAccountTokensScreen(
    id: AccountEntity.Id,
    onFinish: () -> Unit,
) {
    KoshScaffold(
        onNavigateUp = null,
        title = { Text("Discovering Account Tokens...") },
    ) { innerPadding ->
        Box(
            Modifier.fillMaxWidth()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {

            val discovery = rememberDiscoveryAccountTokens(id)

            val onFinishUpdated by rememberUpdatedState(onFinish)

            LaunchedEffect(Unit) {
                discovery.discover()
            }

            discovery.errors?.firstOrNull()?.let {
                AppFailureItem(it) {
                    discovery.retry()
                }
            }

            LaunchedEffect(discovery.finished) {
                if (discovery.finished) {
                    onFinishUpdated()
                }
            }

            LoadingIndicator(true)
        }
    }
}

package kosh.ui.wc

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kosh.domain.models.wc.WcSession
import kosh.presentation.wc.rememberSessions
import kosh.ui.component.LoadingIndicator
import kosh.ui.component.dapp.DappItem
import kosh.ui.component.illustration.Illustration
import kosh.ui.resources.illustrations.WcEmpty

@Composable
fun WcSessionsScreen(
    modifier: Modifier = Modifier,
    onOpen: (WcSession) -> Unit,
) {
    val sessions = rememberSessions()

    WcSessionsContent(
        sessions = sessions.sessions,
        loading = sessions.loading,
        onSelect = onOpen,
        modifier = modifier,
    )
}

@Composable
fun WcSessionsContent(
    sessions: List<WcSession>,
    loading: Boolean,
    onSelect: (WcSession) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier) {
        if (sessions.isEmpty()) {
            item {
                Illustration(
                    WcEmpty(),
                    "WcEmpty",
                    Modifier
                        .fillMaxWidth()
                        .padding(64.dp),
                ) {
                    Text(
                        "Get started by connecting new dapp",
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        items(
            items = sessions,
            key = { it.id.sessionTopic.value }
        ) { session ->
            DappItem(
                modifier = Modifier.animateItemPlacement(),
                dapp = session.dapp,
                onClick = { onSelect(session) },
            )
        }

        item {
            Spacer(Modifier.height(64.dp))
        }
    }

    LoadingIndicator(loading)
}

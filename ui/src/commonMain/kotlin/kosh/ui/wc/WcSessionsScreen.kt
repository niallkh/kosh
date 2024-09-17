package kosh.ui.wc

import androidx.compose.foundation.layout.PaddingValues
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
import kosh.ui.resources.illustrations.DappsEmpty

@Composable
fun WcSessionsScreen(
    paddingValues: PaddingValues,
    onOpen: (WcSession) -> Unit,
) {
    val sessions = rememberSessions()

    WcSessionsContent(
        paddingValues = paddingValues,
        sessions = sessions.sessions,
        loading = sessions.loading,
        onSelect = onOpen,
    )
}

@Composable
fun WcSessionsContent(
    paddingValues: PaddingValues,
    sessions: List<WcSession>,
    loading: Boolean,
    onSelect: (WcSession) -> Unit,
) {
    LazyColumn(
        contentPadding = paddingValues,
    ) {
        if (sessions.isEmpty()) {
            item {
                Illustration(
                    DappsEmpty(),
                    "DappsEmpty",
                    Modifier
                        .fillMaxWidth()
                        .padding(64.dp),
                ) {
                    Text(
                        "Get started by connecting your first DApp",
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
                modifier = Modifier.animateItem(),
                dapp = session.dapp,
                onClick = { onSelect(session) },
            )
        }

        item {
            Spacer(Modifier.height(64.dp))
        }
    }

    LoadingIndicator(
        loading,
        Modifier.padding(paddingValues),
    )
}

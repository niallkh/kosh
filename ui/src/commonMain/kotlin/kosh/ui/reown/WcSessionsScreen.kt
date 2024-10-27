package kosh.ui.reown

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kosh.domain.models.reown.WcSession
import kosh.domain.serializers.ImmutableList
import kosh.presentation.reown.rememberSessions
import kosh.ui.component.illustration.Illustration
import kosh.ui.component.items.DappItem
import kosh.ui.resources.illustrations.DappsEmpty
import kotlinx.collections.immutable.toPersistentList

@Composable
fun WcSessionsScreen(
    paddingValues: PaddingValues,
    scrollBehavior: TopAppBarScrollBehavior,
    onOpen: (WcSession) -> Unit,
) {
    val sessions = rememberSessions()

    WcSessionsContent(
        paddingValues = paddingValues,
        sessions = sessions.sessions,
        init = sessions.init,
        onSelect = onOpen,
        scrollBehavior = scrollBehavior,
    )
}

@Composable
fun WcSessionsContent(
    paddingValues: PaddingValues,
    scrollBehavior: TopAppBarScrollBehavior,
    sessions: ImmutableList<WcSession>,
    init: Boolean,
    onSelect: (WcSession) -> Unit,
) {
    LazyColumn(
        contentPadding = paddingValues,
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {

        when {
            !init -> sessions(
                sessions = List(7) { null }.toPersistentList(),
                onSelect = onSelect
            )

            sessions.isEmpty() -> {
                item {
                    EmptyDappsContent(Modifier.animateItem())
                }
            }

            else -> sessions(
                sessions = sessions,
                onSelect = onSelect
            )
        }

        item {
            Spacer(Modifier.height(64.dp))
        }
    }
}

private fun LazyListScope.sessions(
    sessions: ImmutableList<WcSession?>,
    onSelect: (WcSession) -> Unit,
) {
    items(
        count = sessions.size,
        key = { sessions[it]?.id?.sessionTopic?.value ?: it }
    ) { index ->
        val session = sessions[index]

        DappItem(
            modifier = Modifier.animateItem(),
            dapp = session?.dapp,
            onClick = { session?.let { onSelect(it) } },
        )
    }
}

@Composable
private fun EmptyDappsContent(
    modifier: Modifier = Modifier,
) {
    Illustration(
        DappsEmpty(),
        "DappsEmpty",
        modifier
            .fillMaxWidth()
            .padding(64.dp),
    ) {
        Text(
            "Get started by connecting your first DApp",
            textAlign = TextAlign.Center
        )
    }
}

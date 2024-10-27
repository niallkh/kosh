package kosh.ui.reown

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import kosh.domain.models.reown.WcRequest
import kosh.presentation.reown.rememberRequest
import kosh.ui.component.LoadingIndicator
import kosh.ui.component.scaffold.KoshScaffold
import kosh.ui.failure.AppFailureItem
import kosh.ui.resources.Res
import kosh.ui.resources.wc_request_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun WcRequestScreen(
    id: WcRequest.Id?,
    onResult: (WcRequest) -> Unit,
    onNavigateUp: () -> Unit,
) {
    val request = rememberRequest(id)

    KoshScaffold(
        title = { Text(stringResource(Res.string.wc_request_title)) },
        onNavigateUp = { onNavigateUp() }
    ) { paddingValues ->
        LaunchedEffect(request.request) {
            request.request?.let(onResult)
        }

        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
                .padding(paddingValues)
        ) {
            request.failure?.let {
                AppFailureItem(it) { request.retry() }
            } ?: run {
                Spacer(Modifier)
            }
        }

        LoadingIndicator(
            request.loading,
            Modifier.padding(paddingValues),
        )
    }
}

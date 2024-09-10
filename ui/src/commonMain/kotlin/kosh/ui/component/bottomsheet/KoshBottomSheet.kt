package kosh.ui.component.bottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import kotlinx.coroutines.launch

@Composable
fun KoshModalBottomSheet(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    dragHandle: @Composable (() -> Unit)? = { BottomSheetDefaults.DragHandle() },
    shape: Shape = BottomSheetDefaults.ExpandedShape,
    content: @Composable (DismissCallback) -> Unit,
) {
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    val modalBottomSheetDismiss = remember {
        DismissCallback { onComplete ->
            scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                if (!bottomSheetState.isVisible) {
                    onDismissRequest()
                    onComplete()
                }
            }
        }
    }

    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        sheetState = bottomSheetState,
        dragHandle = dragHandle,
        shape = shape,
        windowInsets = WindowInsets(0)
    ) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            content(modalBottomSheetDismiss)

            Spacer(Modifier.windowInsetsBottomHeight(BottomSheetDefaults.windowInsets))
        }
    }
}

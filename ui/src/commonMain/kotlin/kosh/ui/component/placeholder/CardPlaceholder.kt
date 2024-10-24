package kosh.ui.component.placeholder

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun CardPlaceHolder() {
    Card(
        Modifier
            .size(64.dp)
            .placeholder(true)
    ) {

    }
}

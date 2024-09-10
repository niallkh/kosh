package kosh.ui.transaction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kosh.ui.component.placeholder.placeholder
import kosh.ui.component.text.Header

@Composable
fun PersonalMessageCard(
    message: String?,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {

        Header("Message")

        OutlinedCard(
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .placeholder(message == null),
                text = message ?: "Unknown Personal Message.",
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

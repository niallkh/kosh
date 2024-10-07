package kosh.ui.component.dapp

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dangerous
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kosh.domain.models.reown.WcVerifyContext

@Composable
fun VerifyContextItem(
    verifyContext: WcVerifyContext,
    modifier: Modifier = Modifier,
) {
    when (verifyContext) {
        WcVerifyContext.Threat -> ListItem(
            modifier = modifier.fillMaxWidth(),
            colors = ListItemDefaults.colors(
                containerColor = MaterialTheme.colorScheme.errorContainer,
            ),
            leadingContent = {
                Icon(
                    imageVector = Icons.Default.Dangerous,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )
            },
            headlineContent = {
                Text("Known security risk")
            },
            supportingContent = {
                Text("This domain is flagged as unsafe by multiple security providers. Leave immediately to protect your assets.")
            }
        )

        WcVerifyContext.Mismatch -> ListItem(
            modifier = modifier.fillMaxWidth(),
            colors = ListItemDefaults.colors(
                containerColor = MaterialTheme.colorScheme.errorContainer,
            ),
            leadingContent = {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )
            },
            headlineContent = {
                Text("Domain mismatch")
            },
            supportingContent = {
                Text("This website has a domain that does not match the sender of this request. Approving may lead to loss of funds.")
            }
        )

        WcVerifyContext.Unverified -> ListItem(
            modifier = modifier.fillMaxWidth(),
            colors = ListItemDefaults.colors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            ),
            leadingContent = {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.tertiary
                )
            },
            headlineContent = {
                Text("Unknown domain")
            },
            supportingContent = {
                Text("This domain cannot be verified. Check the request carefully before approving.")
            }
        )

        WcVerifyContext.Match -> Unit
    }
}

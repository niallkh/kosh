package kosh.ui.component.items

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kosh.domain.entities.AccountEntity
import kosh.domain.entities.TokenEntity
import kosh.domain.models.token.Balance
import kosh.ui.component.text.TextAmount

@Composable
fun AccountBalanceItem(
    account: AccountEntity?,
    token: TokenEntity?,
    balance: Balance?,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    AccountItem(
        account = account,
        onClick = onClick,
        modifier = modifier
    ) {
        TextAmount(
            token = token,
            amount = balance?.value,
        )
    }
}

package kosh.presentation.account

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import arrow.optics.Getter
import kosh.domain.entities.AccountEntity
import kosh.domain.state.AppState
import kosh.domain.state.AppStateProvider
import kosh.domain.state.activeAccounts
import kosh.domain.utils.optic
import kosh.presentation.di.di
import kosh.presentation.di.rememberSerializable
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentHashSet


@Composable
fun rememberAccountMultiSelector(
    optic: Getter<AppState, PersistentList<AccountEntity>> = AppState.activeAccounts(),
    initialSelected: Set<AccountEntity.Id> = setOf(),
    appStateProvider: AppStateProvider = di { domain.appStateProvider },
): AccountMultiSelectorState {
    val accounts by appStateProvider.collectAsState().optic(optic)
    var selected by rememberSerializable { mutableStateOf(initialSelected) }

    return AccountMultiSelectorState(
        selected = selected,
        available = accounts,
        select = {
            selected = if (it !in selected) {
                selected.toPersistentHashSet().add(it)
            } else {
                selected.toPersistentHashSet().remove(it)
            }
        },
    )
}

@Immutable
data class AccountMultiSelectorState(
    val selected: Set<AccountEntity.Id>,
    val available: List<AccountEntity>,
    val select: (AccountEntity.Id) -> Unit,
)

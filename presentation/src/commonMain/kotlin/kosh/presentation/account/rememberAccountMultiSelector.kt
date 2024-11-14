package kosh.presentation.account

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kosh.domain.entities.AccountEntity
import kosh.domain.serializers.ImmutableSetSerializer
import kosh.domain.state.AppState
import kosh.domain.state.AppStateProvider
import kosh.domain.state.accounts
import kosh.domain.utils.optic
import kosh.presentation.component.selector.selector
import kosh.presentation.core.di
import kosh.presentation.di.rememberSerializable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.minus
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.plus
import kotlinx.collections.immutable.toPersistentHashSet
import kotlinx.collections.immutable.toPersistentSet


@Composable
fun rememberAccountMultiSelector(
    selectedIds: ImmutableSet<AccountEntity.Id> = persistentSetOf(),
    appStateProvider: AppStateProvider = di { domain.appStateProvider },
): AccountMultiSelectorState {
    val accounts by optic(AppState.accounts()) { appStateProvider.state }
    var selected by rememberSerializable(
        stateSerializer = ImmutableSetSerializer(AccountEntity.Id.serializer())
    ) {
        mutableStateOf(selectedIds)
    }

    selector(selectedIds, ImmutableSetSerializer(AccountEntity.Id.serializer())) {
        selected = selected.toPersistentSet() + it
    }

    return remember {
        object : AccountMultiSelectorState {
            override val selected: ImmutableSet<AccountEntity.Id> get() = selected
            override val accounts: ImmutableList<AccountEntity> get() = accounts
            override fun select(id: AccountEntity.Id) {
                selected = if (id !in selected) {
                    selected.toPersistentHashSet() + id
                } else {
                    selected.toPersistentHashSet() - id
                }
            }
        }
    }
}

@Stable
interface AccountMultiSelectorState {
    val selected: ImmutableSet<AccountEntity.Id>
    val accounts: ImmutableList<AccountEntity>
    fun select(id: AccountEntity.Id)
}

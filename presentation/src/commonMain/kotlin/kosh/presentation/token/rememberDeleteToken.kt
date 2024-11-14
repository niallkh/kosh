package kosh.presentation.token

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import kosh.domain.entities.TokenEntity
import kosh.domain.usecases.token.TokenService
import kosh.presentation.core.di
import kosh.presentation.rememberEffect

@Composable
fun rememberDeleteToken(
    id: TokenEntity.Id,
    tokenService: TokenService = di { domain.tokenService },
): DeleteTokenState {
    val delete = rememberEffect(id) { _: Unit ->
        tokenService.delete(id)
    }

    return DeleteTokenState(
        deleted = delete.done,
        deleting = delete.inProgress,
        delete = { delete(Unit) },
    )
}

@Immutable
data class DeleteTokenState(
    val deleted: Boolean,
    val deleting: Boolean,
    val delete: () -> Unit,
)

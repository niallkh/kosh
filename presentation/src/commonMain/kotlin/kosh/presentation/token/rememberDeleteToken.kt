package kosh.presentation.token

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import kosh.domain.entities.TokenEntity
import kosh.domain.usecases.token.TokenService
import kosh.presentation.Perform
import kosh.presentation.core.di

@Composable
fun rememberDeleteToken(
    id: TokenEntity.Id,
    tokenService: TokenService = di { domain.tokenService },
): DeleteTokenState {
    val delete = Perform(id) {
        tokenService.delete(id)
    }

    return DeleteTokenState(
        deleted = delete.performed,
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

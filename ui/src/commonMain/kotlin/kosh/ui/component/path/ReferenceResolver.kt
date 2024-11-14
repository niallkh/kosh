package kosh.ui.component.path

import androidx.compose.runtime.Composable
import arrow.core.right
import kosh.domain.entities.Reference
import kosh.domain.models.ByteString
import kosh.domain.repositories.ReferenceRepo
import kosh.presentation.core.di
import kosh.presentation.rememberLoad
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.KSerializer

@Composable
inline fun <reified T : Any> Reference<T>.resolve(
    serializer: KSerializer<T>,
    referenceRepo: ReferenceRepo = di { appRepositoriesComponent.referenceRepo },
): T? {
    return rememberLoad(this) {
        withContext(Dispatchers.Default) {
            referenceRepo.get(this@resolve, serializer).right().bind()
        }
    }.result
}

@Composable
inline fun Reference<ByteString>.resolve(
    referenceRepo: ReferenceRepo = di { appRepositoriesComponent.referenceRepo },
): ByteString? {

    return rememberLoad(this) {
        withContext(Dispatchers.Default) {
            referenceRepo.get(this@resolve).right().bind()
        }
    }.result
}

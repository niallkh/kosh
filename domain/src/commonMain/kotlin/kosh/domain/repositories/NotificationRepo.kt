package kosh.domain.repositories

import androidx.compose.runtime.Immutable
import kosh.domain.repositories.NotificationRepo.Type
import kosh.domain.serializers.Uri
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable

interface NotificationRepo : Repository {

    val notifications: Flow<Notification>

    val canceled: Flow<Set<Long>>

    fun send(notification: Notification)

    fun cancel(id: Long)

    enum class Type {
        Wc2,
    }
}

@Serializable
@Immutable
data class Notification(
    val id: Long,
    val title: String,
    val body: String?,
    val uri: Uri,
    val type: Type,
)
package kosh.domain.usecases.notification

import kosh.domain.repositories.Notification
import kotlinx.coroutines.flow.Flow

interface NotificationService {

    val notifications: Flow<Notification>

    val canceled: Flow<Set<Long>>

    fun send(notification: Notification)

    fun cancel(id: Long)
}

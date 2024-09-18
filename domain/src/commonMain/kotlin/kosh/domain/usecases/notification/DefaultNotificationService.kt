package kosh.domain.usecases.notification

import kosh.domain.repositories.Notification
import kosh.domain.repositories.NotificationRepo
import kotlinx.coroutines.flow.Flow

class DefaultNotificationService(
    private val notificationRepo: NotificationRepo,
) : NotificationService {

    override val notifications: Flow<Notification>
        get() = notificationRepo.notifications

    override val cancelled: Flow<Set<Long>>
        get() = notificationRepo.cancelled

    override fun send(notification: Notification) {
        notificationRepo.send(notification)
    }

    override fun cancel(id: Long) {
        notificationRepo.cancel(id)
    }
}

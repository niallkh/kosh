package kosh.data

import co.touchlab.kermit.Logger
import kosh.domain.repositories.Notification
import kosh.domain.repositories.NotificationRepo
import kotlinx.collections.immutable.persistentHashSetOf
import kotlinx.collections.immutable.plus
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

class DefaultNotificationRepo : NotificationRepo {

    private val logger = Logger.withTag("[K]NotificationRepo")

    private val notificationChannel =
        Channel<Notification>(Channel.BUFFERED, BufferOverflow.DROP_OLDEST)

    private val sentIds = MutableStateFlow(persistentHashSetOf<Long>())
    private val cancelledIds = MutableStateFlow(persistentHashSetOf<Long>())

    override val cancelled: Flow<Set<Long>>
        get() = cancelledIds

    override val notifications: Flow<Notification>
        get() = notificationChannel.receiveAsFlow()

    override suspend fun send(notification: Notification) {
        val alreadySent = notification.id in sentIds.getAndUpdate { it + notification.id }
        if (alreadySent) return

        val alreadyCancelled = notification.id in cancelledIds.value
        if (alreadyCancelled) return

        logger.v { "send(id=${notification.id})" }
        notificationChannel.send(notification)
    }

    override fun cancel(id: Long) {
        logger.v { "cancel(id=$id)" }
        cancelledIds.update { it + id }
    }
}

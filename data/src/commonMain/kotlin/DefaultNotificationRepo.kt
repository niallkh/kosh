package kosh.data

import co.touchlab.kermit.Logger
import kosh.domain.repositories.Notification
import kosh.domain.repositories.NotificationRepo
import kotlinx.collections.immutable.persistentHashMapOf
import kotlinx.collections.immutable.plus
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

class DefaultNotificationRepo : NotificationRepo {

    private val logger = Logger.withTag("[K]NotificationRepo")

    private val channel = Channel<Notification>(Channel.BUFFERED, BufferOverflow.DROP_OLDEST)

    private val canceledState = MutableStateFlow(persistentHashMapOf<Long, Boolean>())

    override val canceled: Flow<Set<Long>>
        get() = canceledState.map {
            it.mapNotNull { (id, canceled) -> id.takeIf { canceled } }.toSet()
        }

    override val notifications: Flow<Notification>
        get() = channel.receiveAsFlow()
            .filter { canceledState.value[it.id]?.not() ?: true }

    override fun send(notification: Notification) {
        val canceled = canceledState.getAndUpdate { it.put(notification.id, false) }

        if (notification.id !in canceled) {
            logger.d { "send(id=${notification.id})" }
            channel.trySend(notification)
        }
    }

    override fun cancel(id: Long) {
        logger.d { "cancel(id=$id)" }
        canceledState.update { it + (id to true) }
    }
}

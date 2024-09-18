package kosh.presentation.notification

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kosh.domain.repositories.Notification
import kosh.domain.serializers.ImmutableList
import kosh.domain.usecases.notification.NotificationService
import kosh.presentation.di.di
import kosh.presentation.di.rememberLifecycleState
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch

@Composable
fun rememberNotifications(
    notificationService: NotificationService = di { domain.notificationService },
): NotificationsState {
    var notifications by remember { mutableStateOf(persistentListOf<Notification>()) }

    if (rememberLifecycleState()) {
        LaunchedEffect(Unit) {
            launch {
                notificationService.notifications.collect {
                    notifications = notifications.add(it)
                }
            }
            launch {
                notificationService.cancelled.collect { canceled ->
                    notifications = notifications.removeAll { it.id in canceled }
                }
            }
        }
    }

    return NotificationsState(
        notifications = notifications,
        dismiss = { notification ->
            notifications = notifications.removeAll { it.id == notification.id }
        },
    )
}

data class NotificationsState(
    val notifications: ImmutableList<Notification>,
    val dismiss: (Notification) -> Unit,
)

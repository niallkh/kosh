package kosh.app

import android.Manifest.permission.POST_NOTIFICATIONS
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import co.touchlab.kermit.Logger
import com.eygraber.uri.toAndroidUri
import kosh.domain.repositories.Notification
import kosh.domain.repositories.NotificationRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.minutes

private const val WC_CH_ID = "WC_CH_ID"
private const val OTHER_CH_ID = "OTHER_CH_ID"

class AndroidPushNotifier(
    private val context: Context,
    private val notificationRepo: NotificationRepo,
    private val applicationScope: CoroutineScope,
) {
    private val logger = Logger.withTag("[K]AndroidPushNotifier")
    private val notificationManager = NotificationManagerCompat.from(context)

    fun start() {
        applicationScope.launch {
            ActivityCallbacks.background
                .map { notificationManager.areNotificationsEnabled() }
                .filter { it }
                .collectLatest { collectNotifications() }
        }

        applicationScope.launch {
            notificationRepo.canceled.collectLatest { canceled ->
                val active = notificationManager.activeNotifications.map { it.id }
                canceled.map { it.toInt() }.intersect(active.toSet()).forEach {
                    notificationManager.cancel(it)
                }
            }
        }
    }

    private suspend fun collectNotifications() {
        notificationRepo.notifications.collect { notification ->
            logger.d { "notification()" }

            if (
                ActivityCompat.checkSelfPermission(context, POST_NOTIFICATIONS) !=
                PackageManager.PERMISSION_GRANTED
            ) {
                error("No permission")
            }

            val chId = when (notification.type) {
                NotificationRepo.Type.Wc2 -> createWc2NotificationChannel()
            }

            notificationManager.notify(
                /* id = */ notification.id.toInt(),
                /* notification = */ createNotification(chId, notification),
            )
        }
    }

    private fun createNotification(
        channel: String,
        notification: Notification,
    ): android.app.Notification {
        val intent = Intent(
            /* action = */ Intent.ACTION_VIEW,
            /* uri = */ notification.uri.toAndroidUri(),
            /* packageContext = */ context,
            /* cls = */ KoshActivity::class.java
        )

        val pendingIntent: PendingIntent = PendingIntent
            .getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val not = NotificationCompat.Builder(context, channel).run {
            setSmallIcon(R.drawable.ic_launcher_notification)
            setContentTitle(notification.title)
            if (notification.body != null) {
                setContentText(notification.body)
            }
            setPriority(NotificationCompat.PRIORITY_HIGH)
            setContentIntent(pendingIntent)
            setTimeoutAfter(5.minutes.inWholeMilliseconds)
            setAutoCancel(true)
            build()
        }

        return not
    }

    private fun createWc2NotificationChannel(): String {
        createNotificationChannel(WC_CH_ID, "Wallet Connect Request")
        return WC_CH_ID
    }

    private fun createOtherNotificationChannel(): String {
        createNotificationChannel(OTHER_CH_ID, "Other Notification Channel")
        return OTHER_CH_ID
    }

    private fun createNotificationChannel(
        id: String,
        name: String,
        description: String? = null,
    ) {
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(id, name, importance).apply {
            this.description = description
        }
        notificationManager.createNotificationChannel(channel)
    }
}

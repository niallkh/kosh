package kosh.app

import android.Manifest.permission.POST_NOTIFICATIONS
import android.app.Notification
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
import kosh.domain.repositories.NotificationRepo
import kosh.domain.usecases.notification.NotificationService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.minutes
import kosh.domain.repositories.Notification as AppNotification

private const val WC_CH_ID = "WC_CH_ID"
private const val OTHER_CH_ID = "OTHER_CH_ID"

class AndroidPushNotifier(
    private val context: Context,
    private val notificationService: NotificationService,
    private val applicationScope: CoroutineScope,
) {
    private val logger = Logger.withTag("[K]AndroidPushNotifier")
    private val notificationManager = NotificationManagerCompat.from(context)

    fun start() {
        applicationScope.launch {
            ActivityCallbacks.background
                .map { notificationManager.areNotificationsEnabled() }
                .distinctUntilChanged()
                .filter { it }
                .collectLatest { collectNotifications() }
        }

        applicationScope.launch {
            notificationService.cancelled.collectLatest { canceled ->
                val active = notificationManager.activeNotifications.map { it.id }.toSet()
                canceled.map { it.toInt() }.intersect(active).forEach {
                    notificationManager.cancel(it)
                }
            }
        }
    }

    private suspend fun collectNotifications() {
        notificationService.notifications.collect { appNotification ->
            logger.d { "notification()" }

            if (
                ActivityCompat.checkSelfPermission(context, POST_NOTIFICATIONS) !=
                PackageManager.PERMISSION_GRANTED
            ) {
                error("No permission")
            }

            val chId = when (appNotification.type) {
                NotificationRepo.Type.Wc2 -> createWc2NotificationChannel()
            }

            val notification = createNotification(chId, appNotification)

            notificationManager.notify(
                /* id = */ appNotification.id.toInt(),
                /* notification = */ notification,
            )
        }
    }

    private fun createNotification(
        channel: String,
        appNotification: AppNotification,
    ): Notification {
        val intent = Intent(
            /* action = */ Intent.ACTION_VIEW,
            /* uri = */ appNotification.uri.toAndroidUri(),
            /* packageContext = */ context,
            /* cls = */ KoshActivity::class.java
        )

        val pendingIntent: PendingIntent = PendingIntent
            .getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        return NotificationCompat.Builder(context, channel).run {
            setSmallIcon(R.drawable.ic_launcher_notification)
            setContentTitle(appNotification.title)
            if (appNotification.body != null) {
                setContentText(appNotification.body)
            }
            setPriority(NotificationCompat.PRIORITY_HIGH)
            setContentIntent(pendingIntent)
            setTimeoutAfter(5.minutes.inWholeMilliseconds)
            setAutoCancel(true)
            build()
        }
    }

    private fun createWc2NotificationChannel(): String {
        createNotificationChannel(WC_CH_ID, "WalletConnect Requests")
        return WC_CH_ID
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

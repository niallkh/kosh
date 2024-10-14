package kosh.app

import android.Manifest.permission.POST_NOTIFICATIONS
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import co.touchlab.kermit.Logger
import kosh.domain.repositories.NotificationRepo
import kosh.domain.usecases.notification.NotificationService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.minutes
import kosh.domain.repositories.Notification as AppNotification

private const val WC_CH_ID = "WC_CH_ID"
private const val OTHER_CH_ID = "OTHER_CH_ID"

public class AndroidPushNotifier(
    private val context: Context,
    private val notificationService: NotificationService,
) {
    private val logger = Logger.withTag("[K]AndroidPushNotifier")
    private val notificationManager = NotificationManagerCompat.from(context)

    public suspend fun start() {
        coroutineScope {
            launch(Dispatchers.Main) {
                collectNotifications()
            }

            launch(Dispatchers.Main) {
                collectCancelledNotifications()
            }
        }
    }

    private suspend fun collectNotifications() {
        notificationService.notifications.collect { appNotification ->
            if (
                ActivityCompat.checkSelfPermission(context, POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
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
    }

    private suspend fun collectCancelledNotifications() {
        notificationService.cancelled.collect { canceled ->
            val active = notificationManager.activeNotifications.map { it.id }.toSet()
            canceled.map { it.toInt() }.intersect(active).forEach {
                notificationManager.cancel(it)
            }
        }
    }

    private fun createNotification(
        channel: String,
        appNotification: AppNotification,
    ): Notification {
        val intent = Intent(
            /* action = */ Intent.ACTION_VIEW,
            /* uri = */ Uri.parse(appNotification.uri.toString()),
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
            setWhen(appNotification.time.epochSeconds)
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

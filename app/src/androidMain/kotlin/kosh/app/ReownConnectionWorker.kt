package kosh.app

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.Action
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.ForegroundInfo
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import co.touchlab.kermit.Logger
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlin.random.Random
import kotlin.time.Duration.Companion.minutes

internal const val KOSH_SERVICE_CH_ID = "KOSH_SERVICE_CH_ID"

public class ReownConnectionWorker(
    appContext: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(appContext, workerParams) {

    private val logger = Logger.withTag("[K]ReownConnectionWorker")

    override suspend fun doWork(): Result = coroutineScope {
        logger.v { "Work started" }
        ReownConnectionManager.connect()

        try {
            delay(3.minutes)
        } catch (e: CancellationException) {
            logger.v { "Work canceled" }
            throw e
        } finally {
            ReownConnectionManager.disconnect()
        }

        logger.v { "Work finished" }

        Result.success()
    }

    override suspend fun getForegroundInfo(): ForegroundInfo = ForegroundInfo(
        Random.nextInt(),
        createNotification(),
    )

    private fun createNotification(): Notification {
        val stopIntent = WorkManager.getInstance(applicationContext).createCancelPendingIntent(id)
        return NotificationCompat.Builder(applicationContext, createNotificationChannel()).run {
            setSmallIcon(R.drawable.ic_launcher_notification)
            setContentTitle("Kosh Service")
            setOngoing(true)
            setPriority(NotificationCompat.PRIORITY_LOW)
            addAction(Action.Builder(null, "Stop", stopIntent).build())
            build()
        }
    }

    private fun createNotificationChannel(
        channelId: String = KOSH_SERVICE_CH_ID,
    ): String {
        val channel = NotificationChannel(
            channelId,
            "Kosh Service",
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            this.description = "Kosh Foreground Service for WalletConnect"
        }
        NotificationManagerCompat.from(applicationContext).createNotificationChannel(channel)
        return channelId
    }

    internal companion object {
        fun start(context: Context) {
            val workManager = WorkManager.getInstance(context)

            val request = OneTimeWorkRequestBuilder<ReownConnectionWorker>().run {
                setExpedited(OutOfQuotaPolicy.DROP_WORK_REQUEST)
                build()
            }

            workManager.enqueueUniqueWork(
                "ReownConnection",
                ExistingWorkPolicy.REPLACE,
                request
            )
        }

        fun stop(context: Context) {
            val workManager = WorkManager.getInstance(context)

            workManager.cancelUniqueWork("ReownConnection")
        }
    }
}


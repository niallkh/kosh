package kosh.app

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.ServiceCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import co.touchlab.kermit.Logger
import kosh.app.KoshApplication.Companion.appScope
import kosh.domain.repositories.NotificationRepo.Type
import kosh.domain.repositories.ReownRepo
import kosh.domain.usecases.notification.NotificationService
import kosh.domain.usecases.reown.WcConnectionService
import kosh.domain.usecases.reown.useConnection
import kosh.ui.navigation.deeplink
import kosh.ui.navigation.routes.wcAuthentication
import kosh.ui.navigation.routes.wcProposal
import kosh.ui.navigation.routes.wcRequest
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.minutes
import kosh.domain.repositories.Notification as AppNotification


private const val KOSH_SERVICE_CH_ID = "KOSH_SERVICE_CH_ID"
private const val STOP_ACTION = "STOP_SERVICE"

class KoshService : LifecycleService() {

    private val logger = Logger.withTag("[K]KoshService")

    private lateinit var notificationManager: NotificationManagerCompat
    private lateinit var wcConnectionService: WcConnectionService
    private lateinit var notificationService: NotificationService
    private lateinit var reownRepo: ReownRepo

    private val started = MutableStateFlow(false)

    override fun onCreate() {
        super.onCreate()

        notificationManager = NotificationManagerCompat.from(this)
        wcConnectionService = appScope.domainComponent.wcConnectionService
        notificationService = appScope.domainComponent.notificationService
        reownRepo = appScope.appRepositoriesComponent.reownRepo

        lifecycleScope.launch {
            ActivityCallbacks.background.collectLatest { background ->
                if (background) {
                    delay(5.minutes)
                    stopService()
                }
            }
        }

        lifecycleScope.launch {
            started.collectLatest { started ->
                if (started) {
                    collectWcRequests()
                }
            }
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        logger.d { "onStartCommand(action=${intent?.action})" }

        if (intent?.action == STOP_ACTION) {
            stopService()
            return START_STICKY
        } else {
            startService()
            return START_STICKY
        }
    }

    override fun onTimeout(startId: Int) {
        super.onTimeout(startId)
        stopService()
    }

    private fun startService() {
        createNotificationChannel()
        val notification = createNotification()
        startForeground(notification)
        started.value = true
    }

    private fun stopService() {
        started.value = false
        ServiceCompat.stopForeground(this, 0)
        stopSelf()
    }

    private suspend fun collectWcRequests() = coroutineScope {
        launch {
            wcConnectionService.useConnection()
        }

        launch {
            reownRepo.newRequest
                .collect {
                    notificationService.send(
                        AppNotification(
                            id = it.id.value,
                            title = "WalletConnect Request",
                            uri = deeplink(wcRequest(it.id)),
                            body = "from: ${it.dapp.url ?: it.dapp.name}",
                            type = Type.Wc2,
                        )
                    )
                }
        }

        launch {
            reownRepo.newProposal
                .collect {
                    notificationService.send(
                        AppNotification(
                            id = it.requestId,
                            title = "WalletConnect Proposal",
                            uri = deeplink(wcProposal(it)),
                            body = "from: ${it.dapp.url ?: it.dapp.name}",
                            type = Type.Wc2,
                        )
                    )
                }
        }

        launch {
            reownRepo.newAuthentication
                .collect {
                    notificationService.send(
                        AppNotification(
                            id = it.id.value,
                            title = "WalletConnect Authentication",
                            uri = deeplink(wcAuthentication(it)),
                            body = "from: ${it.dapp.url ?: it.dapp.name}",
                            type = Type.Wc2,
                        )
                    )
                }
        }
    }

    private fun startForeground(notification: Notification) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            ServiceCompat.startForeground(
                this,
                ServiceCompat.STOP_FOREGROUND_REMOVE,
                notification,
                ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE,
            )
        } else {
            ServiceCompat.startForeground(
                this,
                ServiceCompat.STOP_FOREGROUND_REMOVE,
                notification,
                0,
            )
        }
    }

    private fun createNotification(): Notification {
        val stopPendingIntent = PendingIntent.getService(
            this,
            0,
            Intent(
                this,
                KoshService::class.java
            ).apply {
                action = STOP_ACTION
            },
            PendingIntent.FLAG_IMMUTABLE
        )

        val stopAction = NotificationCompat.Action.Builder(
            null,
            "Stop",
            stopPendingIntent
        ).build()

        return NotificationCompat.Builder(this, KOSH_SERVICE_CH_ID).run {
            setSmallIcon(R.drawable.ic_launcher_notification)
            setContentTitle("Kosh Service")
            setOngoing(true)
            setPriority(NotificationCompat.PRIORITY_LOW)
            addAction(stopAction)
            build()
        }
    }

    private fun createNotificationChannel() {
        val importance = NotificationManager.IMPORTANCE_LOW
        val channel = NotificationChannel(KOSH_SERVICE_CH_ID, "Kosh Service", importance).apply {
            this.description = "Kosh Foreground Service for WalletConnect"
        }
        notificationManager.createNotificationChannel(channel)
    }
}

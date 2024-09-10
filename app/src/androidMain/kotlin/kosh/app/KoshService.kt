package kosh.app

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.ServiceCompat
import co.touchlab.kermit.Logger
import kosh.domain.repositories.NotificationRepo.Type
import kosh.domain.usecases.wc.useConnection
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

class KoshService : Service() {

    private val logger = Logger.withTag("[K]KoshService")

    private val notificationManager by lazy { NotificationManagerCompat.from(this) }
    private val applicationScope by lazy {
        KoshApplication.applicationScope.coroutinesComponent.applicationScope
    }
    private val wcConnectionService by lazy {
        KoshApplication.applicationScope.domainComponent.wcConnectionService
    }
    private val notificationService by lazy {
        KoshApplication.applicationScope.domainComponent.notificationService
    }
    private val wcRepo by lazy {
        KoshApplication.applicationScope.appRepositoriesComponent.wcRepo
    }

    private val started = MutableStateFlow(false)

    init {
        applicationScope.launch {
            ActivityCallbacks.background.collectLatest { background ->
                if (background) {
                    delay(5.minutes)
                    stopService()
                }
            }
        }

        applicationScope.launch {
            started.collectLatest { started ->
                if (started) {
                    collectWcRequests()
                }
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

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
            wcRepo.requestQueue()
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
            wcRepo.proposalQueue()
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
            wcRepo.authenticationQueue()
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

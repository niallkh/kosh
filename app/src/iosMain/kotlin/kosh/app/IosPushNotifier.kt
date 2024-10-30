package kosh.app

import co.touchlab.kermit.Logger
import kosh.domain.models.toLibUri
import kosh.domain.repositories.Notification
import kosh.domain.usecases.notification.NotificationService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import platform.UserNotifications.UNAuthorizationOptionAlert
import platform.UserNotifications.UNAuthorizationOptionBadge
import platform.UserNotifications.UNAuthorizationStatusAuthorized
import platform.UserNotifications.UNMutableNotificationContent
import platform.UserNotifications.UNNotificationInterruptionLevel.UNNotificationInterruptionLevelTimeSensitive
import platform.UserNotifications.UNNotificationRequest
import platform.UserNotifications.UNNotificationResponse
import platform.UserNotifications.UNUserNotificationCenter
import platform.UserNotifications.UNUserNotificationCenterDelegateProtocol
import platform.darwin.NSObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

public class IosPushNotifier(
    private val notificationService: NotificationService,
) : NSObject(), UNUserNotificationCenterDelegateProtocol {
    private val logger = Logger.withTag("[K]AndroidPushNotifier")
    private val notificationCenter = UNUserNotificationCenter.currentNotificationCenter()

    internal val uris = Channel<String>(Channel.BUFFERED)

    init {
        notificationCenter.delegate = this
    }

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
        logger.v { "collectNotifications" }
        notificationService.notifications.collect { appNotification ->
            logger.v { "notification received: $appNotification" }
            val settings = suspendCoroutine { cont ->
                notificationCenter.getNotificationSettingsWithCompletionHandler {
                    cont.resume(it)
                }
            }

            if (settings?.authorizationStatus in arrayOf(UNAuthorizationStatusAuthorized)) {
                val request = createNotification(appNotification)

                suspendCoroutine { cont ->
                    notificationCenter.addNotificationRequest(request) {
                        if (it == null) cont.resume(Unit)
                        else cont.resumeWithException(IllegalStateException(it.localizedDescription))
                    }
                }

                logger.v { "notification sent: $appNotification" }
            }
        }
    }

    private suspend fun collectCancelledNotifications() {
        logger.v { "collectCancelledNotifications" }
        notificationService.cancelled.collect { canceled ->
            notificationCenter.removePendingNotificationRequestsWithIdentifiers(canceled.map { it.toString() })
            notificationCenter.removeDeliveredNotificationsWithIdentifiers(canceled.map { it.toString() })
        }
    }

    private fun createNotification(
        appNotification: Notification,
    ): UNNotificationRequest {

        val notificationContent = UNMutableNotificationContent().apply {
            setTitle(appNotification.title)
            appNotification.body?.let {
                setSubtitle(it)
            }
            setUserInfo(mapOf("uri" to appNotification.uri.toLibUri().toString()))
            setInterruptionLevel(UNNotificationInterruptionLevelTimeSensitive)
        }

        return UNNotificationRequest.requestWithIdentifier(
            identifier = appNotification.id.toString(),
            content = notificationContent,
            trigger = null
        )
    }

    override fun userNotificationCenter(
        center: UNUserNotificationCenter,
        didReceiveNotificationResponse: UNNotificationResponse,
        withCompletionHandler: () -> Unit,
    ) {
        val userInfo = didReceiveNotificationResponse.notification.request.content.userInfo
        val uri = userInfo["uri"] as String?
        logger.v { "notification opened $uri" }

        uri?.let { uris.trySend(it) }

        withCompletionHandler()
    }
}

internal fun requestNotificationPermission() {
    val notificationCenter = UNUserNotificationCenter.currentNotificationCenter()

    notificationCenter.requestAuthorizationWithOptions(
        UNAuthorizationOptionAlert or UNAuthorizationOptionBadge
    ) { granted, _ ->
        Logger.d { "requestNotificationPermission, granted=$granted" }
    }
}

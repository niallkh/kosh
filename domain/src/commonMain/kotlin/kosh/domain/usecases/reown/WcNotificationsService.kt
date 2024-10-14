package kosh.domain.usecases.reown

import kosh.domain.repositories.DeeplinkRepo
import kosh.domain.repositories.Notification
import kosh.domain.repositories.NotificationRepo.Type
import kosh.domain.repositories.WcRepo
import kosh.domain.usecases.notification.NotificationService
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class WcNotificationsService(
    private val wcRepo: WcRepo,
    private val notificationService: NotificationService,
    private val deeplinkRepo: DeeplinkRepo,
) {

    suspend fun start() {
        coroutineScope {
            launch {
                wcRepo.newProposal
                    .collect {
                        notificationService.send(
                            Notification(
                                id = it.requestId,
                                title = "WalletConnect Proposal",
                                uri = deeplinkRepo.wcProposal(it),
                                body = "from: ${it.dapp.url ?: it.dapp.name}",
                                type = Type.Wc2,
                            )
                        )
                    }
            }

            launch {
                wcRepo.newAuthentication
                    .collect {
                        notificationService.send(
                            Notification(
                                id = it.id.value,
                                title = "WalletConnect Authentication",
                                uri = deeplinkRepo.wcAuthentication(it),
                                body = "from: ${it.dapp.url ?: it.dapp.name}",
                                type = Type.Wc2,
                            )
                        )
                    }
            }

            launch {
                wcRepo.newRequest
                    .collect {
                        notificationService.send(
                            Notification(
                                id = it.id.value,
                                title = "WalletConnect Request",
                                uri = deeplinkRepo.wcRequest(it),
                                body = "from: ${it.dapp.url ?: it.dapp.name}",
                                type = Type.Wc2,
                            )
                        )
                    }
            }
        }
    }
}

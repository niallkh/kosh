package kosh.domain.repositories

import kosh.domain.models.Uri
import kosh.domain.models.reown.WcAuthentication
import kosh.domain.models.reown.WcRequest
import kosh.domain.models.reown.WcSessionProposal

interface DeeplinkRepo {
    fun wcProposal(proposal: WcSessionProposal): Uri

    fun wcAuthentication(authentication: WcAuthentication): Uri

    fun wcRequest(request: WcRequest): Uri
}

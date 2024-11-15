package kosh.data.reown

import arrow.core.raise.either
import arrow.core.raise.ensureNotNull
import co.touchlab.kermit.Logger
import kosh.domain.failure.WcFailure
import kosh.domain.models.ChainAddress
import kosh.domain.models.ChainId
import kosh.domain.models.Uri
import kosh.domain.models.reown.DappMetadata
import kosh.domain.models.reown.PairingTopic
import kosh.domain.models.reown.SessionTopic
import kosh.domain.models.reown.WcAuthentication
import kosh.domain.models.reown.WcRequest
import kosh.domain.models.reown.WcSession
import kosh.domain.models.reown.WcSessionProposal
import kosh.domain.models.reown.WcVerifyContext
import kosh.libs.reown.AuthenticationRequest
import kosh.libs.reown.Session
import kosh.libs.reown.SessionProposal
import kosh.libs.reown.SessionRequest
import kosh.libs.reown.VerifyContext

internal fun SessionProposal.map() = either {
    WcSessionProposal(
        id = WcSessionProposal.Id(PairingTopic(pairingTopic)),
        requestId = id,
        dapp = DappMetadata(
            name = ensureNotNull(name) {
                WcFailure.WcInvalidRequest.Other("Missing dapp name")
            },
            description = description,
            icon = icon?.let { Uri(it) },
            url = url?.let { Uri(it) },
        ),
        verifyContext = verifyContext.map(),
        required = WcSessionProposal.Namespace(
            chains = required.chains.map { ChainId.fromCaip2(it) },
            events = required.events,
            methods = required.methods,
        ),
        optional = WcSessionProposal.Namespace(
            chains = optional.chains.map { ChainId.fromCaip2(it) },
            events = optional.events,
            methods = optional.methods,
        ),
    )
}

internal fun AuthenticationRequest.map() = either {
    WcAuthentication(
        id = WcAuthentication.Id(id),
        dapp = DappMetadata(
            name = ensureNotNull(name) {
                WcFailure.WcInvalidRequest.Other("Missing dapp name")
            },
            description = description,
            icon = icon?.let { Uri(it) },
            url = url?.let { Uri(it) },
        ),
        verifyContext = verifyContext.map(),
        pairingTopic = PairingTopic(pairingTopic)
    )
}

internal fun SessionRequest.map(logger: Logger) = either {
    WcRequest(
        id = WcRequest.Id(id),
        dapp = DappMetadata(
            name = ensureNotNull(name) {
                WcFailure.WcInvalidRequest.Other("Missing dapp name")
            },
            description = description,
            icon = icon?.let { Uri(it) },
            url = url?.let { Uri(it) },
        ),
        verifyContext = verifyContext.map(),
        sessionTopic = SessionTopic(sessionTopic),
        call = requestCall(
            requestChainId = chainId,
            method = method,
            params = params,
            logger = logger
        ).bind()
    )
}

internal fun Session.map() = either {
    WcSession(
        id = WcSession.Id(SessionTopic(sessionTopic)),
        dapp = DappMetadata(
            name = ensureNotNull(name) {
                WcFailure.WcInvalidRequest.Other("Missing dapp name")
            },
            description = description,
            icon = icon?.let { Uri(it) },
            url = url?.let { Uri(it) },
        ),
        required = WcSessionProposal.Namespace(
            chains = required.chains.map { ChainId.fromCaip2(it) },
            methods = required.methods,
            events = required.events,
        ),
        optional = WcSessionProposal.Namespace(
            chains = optional.chains.map { ChainId.fromCaip2(it) },
            methods = optional.methods,
            events = optional.events,
        ),
        approved = WcSession.Namespace(
            chains = approved.chains.map { ChainId.fromCaip2(it) },
            accounts = approved.accounts.map { ChainAddress.fromCaip10(it) },
            methods = approved.methods,
            events = approved.events,
        ),
    )
}

fun VerifyContext.map() = when (this) {
    VerifyContext.Match -> WcVerifyContext.Match
    VerifyContext.Unverified -> WcVerifyContext.Unverified
    VerifyContext.Mismatch -> WcVerifyContext.Mismatch
    VerifyContext.Threat -> WcVerifyContext.Threat
}

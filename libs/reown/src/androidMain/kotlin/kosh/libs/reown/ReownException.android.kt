package kosh.libs.reown

import com.reown.android.internal.common.exception.CannotFindSequenceForTopic
import com.reown.android.internal.common.exception.ExpiredPairingException
import com.reown.android.internal.common.exception.ExpiredPairingURIException
import com.reown.android.internal.common.exception.MalformedWalletConnectUri
import com.reown.android.internal.common.exception.NoConnectivityException
import com.reown.android.internal.common.exception.PairWithExistingPairingIsNotAllowed
import com.reown.android.internal.common.exception.RequestExpiredException
import com.reown.sign.common.exceptions.InvalidNamespaceException
import com.reown.sign.common.exceptions.SessionProposalExpiredException

fun Throwable.toReownResult(): ReownFailure = when (this) {
    is NoConnectivityException -> ReownFailure.NotConnected(message)

    is MalformedWalletConnectUri -> ReownFailure.PairingUriInvalid(message)
    is PairWithExistingPairingIsNotAllowed -> ReownFailure.AlreadyPaired(message)

    is ExpiredPairingException -> ReownFailure.NotFound(message)
    is ExpiredPairingURIException -> ReownFailure.NotFound(message)
    is CannotFindSequenceForTopic -> ReownFailure.NotFound(message)
    is RequestExpiredException -> ReownFailure.NotFound(message)
    is SessionProposalExpiredException -> ReownFailure.NotFound(message)

    is InvalidNamespaceException -> ReownFailure.InvalidNamespace(message)

    else -> when {
        "Pairing URI expired" in message.orEmpty() -> ReownFailure.NotFound(message)
        else -> ReownFailure.Other(message)
    }
}

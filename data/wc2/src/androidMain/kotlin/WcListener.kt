package kosh.data.wc2

import com.walletconnect.sign.client.Sign
import kotlinx.collections.immutable.PersistentMap
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

typealias Verified<T> = Pair<T, Sign.Model.VerifyContext>

interface WcListener {
    val connectionState: Flow<Boolean>

    val authentication: Flow<Verified<Sign.Model.SessionAuthenticate>>
    val proposal: Flow<Verified<Sign.Model.SessionProposal>>
    val request: Flow<Verified<Sign.Model.SessionRequest>>

    val proposalRequestIds: MutableStateFlow<PersistentMap<String, Long>>
    val smthChanged: Flow<Any>

    fun onChanged()
}

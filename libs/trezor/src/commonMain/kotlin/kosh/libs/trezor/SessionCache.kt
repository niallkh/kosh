package kosh.libs.trezor

import okio.ByteString
import kotlin.jvm.JvmInline

@JvmInline
value class SessionId(val value: ByteString)

class SessionsCache {

    private val cache = mutableMapOf<String, SessionId>()

    fun get(id: String): SessionId? = cache[id]

    fun set(id: String, sessionId: SessionId) {
        cache[id] = sessionId
    }
}

internal class SessionCache(
    private val sessionsCache: SessionsCache,
    private val id: String,
) {
    fun get(): SessionId? = sessionsCache.get(id)

    fun set(sessionId: SessionId) {
        sessionsCache.set(id, sessionId)
    }
}

internal fun SessionsCache.create(id: String) = SessionCache(this, id)

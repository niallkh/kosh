package kosh.libs.trezor

import okio.ByteString
import kotlin.jvm.JvmInline

@JvmInline
value class SessionId(val value: ByteString)

class SessionsCache {

    private val cache = mutableMapOf<Long, SessionId>()

    fun get(id: Long): SessionId? = cache[id]

    fun set(id: Long, sessionId: SessionId) {
        cache[id] = sessionId
    }
}

internal class SessionCache(
    private val sessionsCache: SessionsCache,
    private val id: Long,
) {
    fun get(): SessionId? = sessionsCache.get(id)

    fun set(sessionId: SessionId) {
        sessionsCache.set(id, sessionId)
    }
}

internal fun SessionsCache.create(id: Long) = SessionCache(this, id)

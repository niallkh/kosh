package kosh.domain.uuid

import org.kotlincrypto.core.digest.Digest
import org.kotlincrypto.hash.sha1.SHA1
import kotlin.experimental.and
import kotlin.experimental.or
import kotlin.uuid.Uuid

val UuidNil = Uuid.fromLongs(0, 0)

fun uuid4(): Uuid = Uuid.random()

fun uuid5(
    namespace: Uuid = UuidNil,
    name: String,
): Uuid = nameBasedUuidOf(
    namespace = namespace,
    name = name,
    digest = SHA1(),
    version = 5
)

val Uuid.leastSignificantBits
    inline get() = toLongs { _, leastSignificantBits -> leastSignificantBits }

val Uuid.mostSignificantBits
    inline get() = toLongs { mostSignificantBits, _ -> mostSignificantBits }

private fun nameBasedUuidOf(namespace: Uuid, name: String, digest: Digest, version: Int): Uuid {
    digest.update(namespace.toByteArray())
    digest.update(name.encodeToByteArray())
    val hashedBytes = digest.digest()
    hashedBytes[6] = hashedBytes[6]
        .and(0b00001111)
        .or(version.shl(4).toByte())
    hashedBytes[8] = hashedBytes[8]
        .and(0b00111111)
        .or(-0b10000000)
    return Uuid.fromByteArray(hashedBytes.copyOf(16))
}

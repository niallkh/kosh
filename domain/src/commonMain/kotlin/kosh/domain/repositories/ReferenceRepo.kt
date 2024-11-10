package kosh.domain.repositories

import arrow.resilience.SagaScope
import kosh.domain.entities.Reference
import kosh.domain.models.ByteString
import kotlinx.serialization.KSerializer

interface ReferenceRepo {

    suspend fun get(
        reference: Reference<ByteString>,
    ): ByteString?

    suspend fun <T : Any> get(
        reference: Reference<T>,
        serializer: KSerializer<T>,
    ): T?

    suspend fun save(
        value: ByteString,
    ): Reference<ByteString>

    suspend fun <T : Any> save(
        value: T,
        serializer: KSerializer<T>,
    ): Reference<T>

    suspend fun remove(
        reference: Reference<*>,
    )
}

suspend fun SagaScope.save(
    referenceRepo: ReferenceRepo,
    value: ByteString,
): Reference<ByteString> = saga({
    referenceRepo.save(value)
}) {
    referenceRepo.remove(it)
}

suspend fun <T : Any> SagaScope.save(
    referenceRepo: ReferenceRepo,
    value: T,
    serializer: KSerializer<T>,
): Reference<T> = saga({
    referenceRepo.save(value, serializer)
}) {
    referenceRepo.remove(it)
}

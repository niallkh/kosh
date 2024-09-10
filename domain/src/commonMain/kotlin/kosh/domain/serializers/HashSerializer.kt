package kosh.domain.serializers

import kosh.domain.models.Hash
import kotlinx.serialization.KSerializer

object HashSerializer : KSerializer<Hash> by serializer(
    ByteStringSerializer,
    { it.value },
    { Hash(it) }
)

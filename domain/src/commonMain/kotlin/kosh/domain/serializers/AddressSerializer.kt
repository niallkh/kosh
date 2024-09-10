package kosh.domain.serializers

import kosh.domain.models.Address
import kotlinx.serialization.KSerializer

object AddressSerializer : KSerializer<Address> by serializer(
    ByteStringSerializer,
    { it.value },
    { Address(it) }
)


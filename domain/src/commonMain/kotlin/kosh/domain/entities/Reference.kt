package kosh.domain.entities

import kosh.domain.serializers.Uuid
import kotlinx.serialization.Serializable

@Serializable
data class Reference<T : Any>(val key: Uuid)

package kosh.domain.entities

import kosh.domain.serializers.Uuid
import kotlinx.datetime.Instant

interface Entity {
    val id: Id<out Entity>
    val createdAt: Instant
    val modifiedAt: Instant

    sealed interface Id<Entity : Any> {
        val value: Uuid
    }
}


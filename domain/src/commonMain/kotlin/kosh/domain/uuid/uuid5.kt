package kosh.domain.uuid

import com.benasher44.uuid.Uuid

expect fun uuid5(namespace: Uuid, name: String): Uuid


package kosh.domain.uuid

import com.benasher44.uuid.Uuid
import com.benasher44.uuid.uuid5Of

actual fun uuid5(namespace: Uuid, name: String): Uuid = uuid5Of(namespace, name)

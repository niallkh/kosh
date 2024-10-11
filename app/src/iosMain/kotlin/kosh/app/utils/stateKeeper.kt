package kosh.app.utils

import com.arkivanov.essenty.statekeeper.SerializableContainer
import kotlinx.serialization.json.Json
import platform.Foundation.NSCoder
import platform.Foundation.NSString
import platform.Foundation.decodeTopLevelObjectOfClass
import platform.Foundation.encodeObject

internal val json = Json {
    allowStructuredMapKeys = true
}

public fun save(coder: NSCoder, state: SerializableContainer) {
    coder.encodeObject(
        `object` = json.encodeToString(SerializableContainer.serializer(), state),
        forKey = "kosh.app.state"
    )
}

public fun restore(coder: NSCoder): SerializableContainer? {
    val decoded = coder.decodeTopLevelObjectOfClass(
        aClass = NSString,
        forKey = "kosh.app.state",
        error = null
    ) as String?

    return try {
        decoded?.let {
            json.decodeFromString(SerializableContainer.serializer(), decoded)
        }
    } catch (e: Exception) {
        null
    }
}

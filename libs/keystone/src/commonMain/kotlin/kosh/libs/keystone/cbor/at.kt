package kosh.libs.keystone.cbor

operator fun CborElement.get(path: String): CborElement? = at(path.split('.'))

fun CborElement.at(components: List<String>): CborElement? =
    when (val key = components.firstOrNull()?.let(CborElement::CborTextString)) {
        null -> this
        else -> when (this) {
            is CborElement.CborMap -> {
                val index = key.value.toULongOrNull()?.let(CborElement::CborUInt)

                get(index ?: key)?.at(components.drop(1))
            }

            is CborElement.CborArray -> {
                val index = key.value.toULongOrNull()
                    ?: error("Invalid index: $key")

                get(index.toInt()).at(components.drop(1))
            }

            else -> error("Invalid path: $components")
        }
    }

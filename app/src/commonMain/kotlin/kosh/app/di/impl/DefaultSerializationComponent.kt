package kosh.app.di.impl

import kosh.app.di.SerializationComponent
import kosh.domain.core.provider
import kotlinx.serialization.cbor.Cbor
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.PolymorphicModuleBuilder
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.SerializersModuleBuilder
import kotlinx.serialization.modules.polymorphic

class DefaultSerializationComponent : SerializationComponent {

    override val json: Json by provider {
        Json {
            serializersModule = serializationModule
            ignoreUnknownKeys = true
            isLenient = true
        }
    }
    override val cbor: Cbor by provider {
        Cbor {
            serializersModule = serializationModule
            ignoreUnknownKeys = true
        }
    }

    private val serializationModule by provider {
        SerializersModule {}
    }
}

private inline fun <reified T : Any> SerializersModuleBuilder.polymorphic(
    builderAction: PolymorphicModuleBuilder<T>.() -> Unit,
) {
    polymorphic(T::class) {
        builderAction()
    }
}

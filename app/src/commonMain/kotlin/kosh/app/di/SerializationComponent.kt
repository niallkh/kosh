package kosh.app.di

import kotlinx.serialization.cbor.Cbor
import kotlinx.serialization.json.Json

public interface SerializationComponent {

    public val json: Json

    public val cbor: Cbor
}

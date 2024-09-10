package kosh.app.di

import kotlinx.serialization.cbor.Cbor
import kotlinx.serialization.json.Json

interface SerializationComponent {

    val json: Json

    val cbor: Cbor
}

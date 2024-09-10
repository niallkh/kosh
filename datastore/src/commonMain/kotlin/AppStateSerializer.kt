package kosh.datastore

import androidx.datastore.core.okio.OkioSerializer
import kosh.domain.state.AppState
import kotlinx.serialization.cbor.Cbor
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray
import okio.BufferedSink
import okio.BufferedSource

class AppStateSerializer(private val cbor: Cbor) : OkioSerializer<AppState> {

    override val defaultValue
        get() = AppState.Default

    override suspend fun readFrom(source: BufferedSource): AppState {
        return cbor.decodeFromByteArray(source.readByteArray())
    }

    override suspend fun writeTo(t: AppState, sink: BufferedSink) {
        sink.write(cbor.encodeToByteArray(t))
    }
}

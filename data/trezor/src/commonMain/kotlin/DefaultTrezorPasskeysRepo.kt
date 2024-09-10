package kosh.data.trezor

import arrow.core.Either
import arrow.core.right
import co.touchlab.kermit.Logger
import com.satoshilabs.trezor.lib.protobuf.WebAuthnCredentials
import kosh.domain.failure.TrezorFailure
import kosh.domain.models.ByteString
import kosh.domain.models.trezor.Trezor
import kosh.domain.models.trezor.TrezorPasskey
import kosh.domain.repositories.TrezorListener
import kosh.domain.repositories.TrezorPasskeysRepo
import kosh.libs.trezor.TrezorManager
import kosh.libs.trezor.cmds.addCredential
import kosh.libs.trezor.cmds.deleteCredential
import kosh.libs.trezor.cmds.listCredentials
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import okio.ByteString.Companion.decodeBase64

class DefaultTrezorPasskeysRepo(
    private val trezorManager: TrezorManager,
    private val json: Json = Json,
) : TrezorPasskeysRepo {

    private val logger = Logger.withTag("[K]TrezorPasskeysRepo")

    override fun passkeys(
        listener: TrezorListener,
        trezor: Trezor,
    ): Flow<Either<TrezorFailure, TrezorPasskey>> = flow {
        trezorManager.open(
            id = trezor.id.value,
            listener = TrezorListenerAdapter(listener)
        ).use { connection ->
            connection.init()

            connection.listCredentials()
                .map { trezorPasskey(it) }
                .forEach { emit(it.right()) }
        }
    }
        .flowOn(Dispatchers.Default)
        .catchTrezorFailure(logger)


    override suspend fun deletePasskey(
        listener: TrezorListener,
        trezor: Trezor,
        indices: ImmutableSet<TrezorPasskey.Index>,
    ): Either<TrezorFailure, Unit> = withContext(Dispatchers.Default) {
        Either.catch {
            trezorManager.open(
                id = trezor.id.value,
                listener = TrezorListenerAdapter(listener)
            ).use { connection ->
                connection.init()

                indices.forEach {
                    connection.deleteCredential(it.value)
                }
            }
        }
            .mapTrezorFailure(logger)
    }


    override suspend fun addPasskeys(
        listener: TrezorListener,
        trezor: Trezor,
        ids: ImmutableList<TrezorPasskey.Id>,
    ): Either<TrezorFailure, Unit> = Either.catch {
        trezorManager.open(
            id = trezor.id.value,
            listener = TrezorListenerAdapter(listener)
        ).use { connection ->
            connection.init()

            ids.forEach {
                connection.addCredential(it.value.bytes())
            }
        }
    }
        .mapTrezorFailure(logger)

    override suspend fun encodePasskeys(
        passkeys: ImmutableList<TrezorPasskey>,
    ): String = withContext(Dispatchers.Default) {
        json.encodeToString(
            passkeys.map {
                TrezorPasskeysDto(
                    id = it.id.value.bytes(),
                    rpName = it.rpName,
                    userName = it.userName,
                    userDisplayName = it.userDisplayName,
                )
            }
        )
    }

    override suspend fun decodePasskeys(
        content: String,
    ): ImmutableList<TrezorPasskey> = withContext(Dispatchers.Default) {
        json.decodeFromString<List<TrezorPasskeysDto>>(content)
            .mapIndexed { index, dto -> trezorPasskey(index, dto) }
            .toImmutableList()
    }

    private fun trezorPasskey(
        credential: WebAuthnCredentials.WebAuthnCredential,
    ) = TrezorPasskey(
        index = TrezorPasskey.Index(credential.index!!),
        id = TrezorPasskey.Id(ByteString(credential.id!!)),
        rpId = credential.rp_id,
        rpName = credential.rp_name,
        userId = credential.user_id?.let { ByteString(it) },
        userName = credential.user_name,
        userDisplayName = credential.user_display_name,
        creationTime = credential.creation_time,
        hmacSecret = credential.hmac_secret,
        useSignCount = credential.use_sign_count,
        algorithm = credential.algorithm,
        curve = credential.curve,
    )

    private fun trezorPasskey(
        index: Int,
        dto: TrezorPasskeysDto,
    ) = TrezorPasskey(
        index = TrezorPasskey.Index(index),
        id = TrezorPasskey.Id(ByteString(dto.id)),
        rpName = dto.rpName,
        userName = dto.userName,
        userDisplayName = dto.userDisplayName,
    )

    @Serializable
    private data class TrezorPasskeysDto(
        @Serializable(ByteStringBase64Serializer::class)
        val id: okio.ByteString,
        val rpName: String? = null,
        val userName: String? = null,
        val userDisplayName: String? = null,
    )
}

private object ByteStringBase64Serializer : KSerializer<okio.ByteString> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("okio.ByteString", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: okio.ByteString) {
        encoder.encodeString(value.base64())
    }

    override fun deserialize(decoder: Decoder): okio.ByteString {
        return decoder.decodeString().decodeBase64() ?: error("Expected base 64 string")
    }
}

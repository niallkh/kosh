package kosh.ui.resources

import arrow.core.raise.catch
import co.touchlab.kermit.Logger
import kosh.domain.models.Address
import kosh.domain.models.ChainId
import kosh.domain.models.token.TokenMetadata
import kosh.domain.repositories.TokenListsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okio.Buffer
import okio.ByteString
import okio.ByteString.Companion.decodeHex
import okio.ByteString.Companion.toByteString
import kosh.domain.models.ByteString as DomainByteString

public class DefaultTokenListsRepo : TokenListsRepo {

    private val logger = Logger.withTag("[K]TokenListsRepo")

    override suspend fun tokens(
        chainId: ChainId,
    ): Flow<TokenMetadata> = flow {

        val bytes = catch({
            Res.readBytes("files/token-lists/${chainId.value}.csv").toByteString()
        }) {
            logger.w(it) { "Error happened during reading token list for ${chainId.value}" }
            ByteString.EMPTY
        }

        val buffer = Buffer().apply { write(bytes) }

        var line = buffer.readUtf8Line() // ignore first row

        while (line != null) {
            line = buffer.readUtf8Line()?.takeUnless { it.isEmpty() } ?: break

            val parts = line.split(",")

            emit(
                TokenMetadata(
                    chainId = chainId,
                    name = parts[0],
                    symbol = parts[1],
                    decimals = parts[2].toUByte(),
                    address = Address(DomainByteString(parts[3].decodeHex())),
                    icon = Icons.icon(parts[4]),
                    type = TokenMetadata.Type.valueOf(parts[5].uppercase())
                )
            )
        }
    }
        .flowOn(Dispatchers.Default)
}

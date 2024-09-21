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
import kotlinx.io.Buffer
import kotlinx.io.bytestring.ByteString
import kotlinx.io.bytestring.hexToByteString
import kotlinx.io.bytestring.unsafe.UnsafeByteStringOperations
import kotlinx.io.readLine
import kotlinx.io.write
import kosh.domain.models.ByteString as DomainByteString

public class DefaultTokenListsRepo : TokenListsRepo {

    private val logger = Logger.withTag("[K]TokenListsRepo")

    override suspend fun tokens(
        chainId: ChainId,
    ): Flow<TokenMetadata> = flow {

        val bytes = catch({
            val bytes = Res.readBytes("files/token-lists/${chainId.value}.csv")
            UnsafeByteStringOperations.wrapUnsafe(bytes)
        }) {
            logger.w(it) { "Error happened during reading token list for ${chainId.value}" }
            ByteString()
        }

        val buffer = Buffer().apply { write(bytes) }

        var line = buffer.readLine() // ignore first row

        while (line != null) {
            line = buffer.readLine()?.takeUnless { it.isEmpty() } ?: break

            val parts = line.split(",")

            emit(
                TokenMetadata(
                    chainId = chainId,
                    name = parts[0],
                    symbol = parts[1],
                    decimals = parts[2].toUByte(),
                    address = Address(DomainByteString(parts[3].hexToByteString())),
                    icon = Icons.icon(parts[4]),
                    type = TokenMetadata.Type.valueOf(parts[5].uppercase())
                )
            )
        }
    }
        .flowOn(Dispatchers.Default)
}

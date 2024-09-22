package kosh.domain.repositories

import kosh.domain.models.ChainId
import kosh.domain.models.Uri

interface NetworkRepo : Repository {

    suspend fun validateRpc(
        chainId: ChainId,
        rpc: Uri,
    ): Boolean
}

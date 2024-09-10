package kosh.domain.repositories

import com.eygraber.uri.Uri
import kosh.domain.models.ChainId

interface NetworkRepo : Repository {

    suspend fun validateRpc(
        chainId: ChainId,
        rpc: Uri,
    ): Boolean
}

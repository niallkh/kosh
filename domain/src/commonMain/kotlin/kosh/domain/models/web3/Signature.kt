package kosh.domain.models.web3

import androidx.compose.runtime.Immutable
import kosh.domain.models.Address
import kosh.domain.models.ByteString
import kosh.domain.models.Hash
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class Signature(
    val signer: Address,
    val data: ByteString,
    val hash: Hash,
)

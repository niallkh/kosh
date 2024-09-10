package kosh.libs.ipfs.car

import kosh.libs.ipfs.cid.Cid

internal data class Link(
    val cid: Cid,
    val name: String,
    val size: Long,
)

package kosh.libs.ipfs.cid

public enum class Codec(internal val type: Long) {
    Raw(0x55),
    DagProtobuf(0x70),
    DagCbor(0x71),
    Libp2pKey(0x72),
    EthereumBlock(0x90),
    EthereumTx(0x91),
    BitcoinBlock(0xb0),
    BitcoinTx(0xb1),
    ZcashBlock(0xc0),
    ZcashTx(0xc1);
}

import okio.Buffer
import okio.ByteString
import okio.ByteString.Companion.decodeHex

public object SingletonAbi {

    public val address: Value.Address =
        Value.Address("914d7Fec6aaC8cd542e72Bca78B30650d45643d7".decodeHex())

    public val erc4337FactoryAdapter: Value.Address =
        Value.Address("310D1760cd2Dd313B11389165dbbB3484FE270EB".decodeHex())

    public fun deploy(
        initCode: ByteString,
        salt: ByteString,
    ): FunctionCall<Value.Address> = DefaultFunctionCall(
        encoder = { salt.keccak256() + initCode },
        decoder = { Value.Address(it.substring(0, 20)) }
    )

    public fun erc4337Deploy(
        initCode: ByteString,
        salt: ByteString,
    ): FunctionCall<Value.Address> = DefaultFunctionCall(
        encoder = { erc4337FactoryAdapter.value + salt.keccak256() + initCode },
        decoder = { Value.Address(it.substring(12, 32)) }
    )

    public fun predictAddress(
        salt: ByteString,
        initCode: ByteString,
    ): Value.Address = Buffer().apply {
        writeByte(0xff)
        write(address.value)
        write(salt.keccak256())
        write(initCode.keccak256())
    }
        .readByteString()
        .keccak256()
        .substring(12, 32).abiAddress
}

package kosh.eth.proposals

import kotlinx.io.bytestring.ByteString


public interface FunctionCall<out T : Any> {

    public fun encode(): ByteString

    public fun decode(data: ByteString): T
}

public class DefaultFunctionCall<out T : Any>(
    private val encoder: () -> ByteString,
    private val decoder: (ByteString) -> T,
) : FunctionCall<T> {
    override fun encode(): ByteString = encoder()
    override fun decode(data: ByteString): T = decoder(data)
}

package kosh.eth.proposals

import kosh.eth.abi.Value.Address
import okio.ByteString

public interface ContractCall<out T : Any> {

    public val target: Address

    public val result: Result<T>

    public fun encode(): ByteString

    public fun decodeResult(data: ByteString)

    public fun decodeError(data: ByteString)
}

public class DefaultContractCall<T : Any>(
    override val target: Address,
    private val functionCall: FunctionCall<T>,
) : ContractCall<T> {

    override var result: Result<T> = Result.failure(Throwable("Call didn't happen yet"))
        private set

    override fun encode(): ByteString = functionCall.encode()

    override fun decodeResult(data: ByteString) {
        result = runCatching {
            functionCall.decode(data)
        }
    }

    override fun decodeError(data: ByteString) {
        result = Result.failure(Throwable("Call failed: ${data.utf8()}"))
    }
}

public fun <T : Any> FunctionCall<T>.at(contract: Address): ContractCall<T> =
    DefaultContractCall(contract, this)

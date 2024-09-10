package kosh.eth.proposals

import okio.ByteString

@Deprecated("")
public interface EventFilter<T : Any> {

    public fun create(): List<ByteString?>

    public fun decode(
        topics: List<ByteString>,
        data: ByteString,
    ): T
}

public data class DefaultEventFilter<T : Any>(
    private val filter: () -> List<ByteString?>,
    private val decoder: (List<ByteString>, ByteString) -> T,
) : EventFilter<T> {
    override fun create(): List<ByteString?> = filter()

    override fun decode(topics: List<ByteString>, data: ByteString): T = decoder(topics, data)
}

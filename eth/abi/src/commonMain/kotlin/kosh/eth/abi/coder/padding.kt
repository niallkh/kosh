package kosh.eth.abi.coder

import okio.BufferedSink

public fun BufferedSink.padding(size: Int) {
    repeat(size) {
        writeByte(0)
    }
}

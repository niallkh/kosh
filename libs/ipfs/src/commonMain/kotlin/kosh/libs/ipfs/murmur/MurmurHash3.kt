package kosh.libs.ipfs.murmur

internal class MurmurHash3(private val seed: UInt = 0u) {

    internal fun hash128x64(key: ByteArray): Array<ULong> {
        var h1 = seed.toULong()
        var h2 = seed.toULong()
        val len = key.size
        val nblocks = len / 16

        for (i in 0 until nblocks * 16 step 16) {
            val k1 = key.getLittleEndianLong(i)
            val k2 = key.getLittleEndianLong(i + 8)

            h1 = h1 xor k1.mix(R1_128x64, C1_128x64, C2_128x64)
            h1 = h1.rotateLeft(R2_128x64)
            h1 += h2
            h1 = h1 * M_128x64 + N1_128x64

            h2 = h2 xor k2.mix(R3_128x64, C2_128x64, C1_128x64)
            h2 = h2.rotateLeft(R1_128x64)
            h2 += h1
            h2 = h2 * M_128x64 + N2_128x64
        }

        val index = nblocks * 16
        val rem = len - index
        var k1 = 0uL
        var k2 = 0uL
        if (rem == 15) {
            k2 = k2 xor (key.getULong(index + 14) shl 48)
        }
        if (rem >= 14) {
            k2 = k2 xor (key.getULong(index + 13) shl 40)
        }
        if (rem >= 13) {
            k2 = k2 xor (key.getULong(index + 12) shl 32)
        }
        if (rem >= 12) {
            k2 = k2 xor (key.getULong(index + 11) shl 24)
        }
        if (rem >= 11) {
            k2 = k2 xor (key.getULong(index + 10) shl 16)
        }
        if (rem >= 10) {
            k2 = k2 xor (key.getULong(index + 9) shl 8)
        }
        if (rem >= 9) {
            k2 = k2 xor key.getULong(index + 8)
            h2 = h2 xor k2.mix(R3_128x64, C2_128x64, C1_128x64)
        }
        if (rem >= 8) {
            k1 = k1 xor (key.getULong(index + 7) shl 56)
        }
        if (rem >= 7) {
            k1 = k1 xor (key.getULong(index + 6) shl 48)
        }
        if (rem >= 6) {
            k1 = k1 xor (key.getULong(index + 5) shl 40)
        }
        if (rem >= 5) {
            k1 = k1 xor (key.getULong(index + 4) shl 32)
        }
        if (rem >= 4) {
            k1 = k1 xor (key.getULong(index + 3) shl 24)
        }
        if (rem >= 3) {
            k1 = k1 xor (key.getULong(index + 2) shl 16)
        }
        if (rem >= 2) {
            k1 = k1 xor (key.getULong(index + 1) shl 8)
        }
        if (rem >= 1) {
            k1 = k1 xor key.getULong(index)
            h1 = h1 xor k1.mix(R1_128x64, C1_128x64, C2_128x64)
        }

        h1 = h1 xor len.toULong()
        h2 = h2 xor len.toULong()

        h1 += h2
        h2 += h1

        h1 = h1.fmix()
        h2 = h2.fmix()

        h1 += h2
        h2 += h1

        return arrayOf(h1, h2)
    }

    private fun ByteArray.getLittleEndianLong(index: Int): ULong {
        return this.getULong(index) or
                (this.getULong(index + 1) shl 8) or
                (this.getULong(index + 2) shl 16) or
                (this.getULong(index + 3) shl 24) or
                (this.getULong(index + 4) shl 32) or
                (this.getULong(index + 5) shl 40) or
                (this.getULong(index + 6) shl 48) or
                (this.getULong(index + 7) shl 56)
    }

    private fun ULong.mix(r: Int, c1: ULong, c2: ULong): ULong {
        var k = this
        k *= c1
        k = k.rotateLeft(r)
        k *= c2
        return k
    }

    private fun ULong.fmix(): ULong {
        var h = this
        h = h xor (h shr 33)
        h *= 0xff51afd7ed558ccduL
        h = h xor (h shr 33)
        h *= 0xc4ceb9fe1a85ec53uL
        h = h xor (h shr 33)
        return h
    }

    private fun ByteArray.getULong(index: Int) = get(index).toUByte().toULong()

    private companion object {
        private const val C1_128x64: ULong = 0x87c37b91114253d5uL
        private const val C2_128x64: ULong = 0x4cf5ad432745937fuL

        private const val R1_128x64: Int = 31
        private const val R2_128x64: Int = 27
        private const val R3_128x64: Int = 33

        private const val M_128x64: ULong = 5u
        private const val N1_128x64: ULong = 0x52dce729u
        private const val N2_128x64: ULong = 0x38495ab5u
    }
}

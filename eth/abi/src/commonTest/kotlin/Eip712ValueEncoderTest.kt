import kosh.eth.abi.Eip712
import kosh.eth.abi.coder.eip712SignHash
import kosh.eth.abi.coder.eip712StructHash
import okio.ByteString.Companion.decodeHex
import kotlin.test.Test
import kotlin.test.assertEquals

class Eip712ValueEncoderTest {

    @Test
    fun testType() {
        val eip712 = Eip712.fromJson(typedDataJson)

        assertEquals(
            expected = "c52c0ee5d84264471806290a3f2c4cecfc5490626bf912d01f240d7a274b371e".decodeHex(),
            actual = eip712.message.type.eip712StructHash(eip712.message.value)
        )
        assertEquals(
            expected = "f2cee375fa42b42143804025fc449deafd50cc031ca257e0b194a650a912090f".decodeHex(),
            actual = eip712.domain.type.eip712StructHash(eip712.domain.value)
        )
        assertEquals(
            expected = "be609aee343fb3c4b28e1df9e632fca64fcfaede20f02e86244efddf30957bd2".decodeHex(),
            actual = eip712.eip712SignHash()
        )
    }
}

val typedDataJson = """
    {
      "types": {
        "EIP712Domain": [
          {
            "name": "name",
            "type": "string"
          },
          {
            "name": "version",
            "type": "string"
          },
          {
            "name": "chainId",
            "type": "uint256"
          },
          {
            "name": "verifyingContract",
            "type": "address"
          }
        ],
        "Person": [
          {
            "name": "name",
            "type": "string"
          },
          {
            "name": "wallet",
            "type": "address"
          }
        ],
        "Mail": [
          {
            "name": "from",
            "type": "Person"
          },
          {
            "name": "to",
            "type": "Person"
          },
          {
            "name": "contents",
            "type": "string"
          }
        ]
      },
      "primaryType": "Mail",
      "domain": {
        "name": "Ether Mail",
        "version": "1",
        "chainId": 1,
        "verifyingContract": "0xCcCCccccCCCCcCCCCCCcCcCccCcCCCcCcccccccC"
      },
      "message": {
        "from": {
          "name": "Cow",
          "wallet": "0xCD2a3d9F938E13CD947Ec05AbC7FE734Df8DD826"
        },
        "to": {
          "name": "Bob",
          "wallet": "0xbBbBBBBbbBBBbbbBbbBbbbbBBbBbbbbBbBbbBBbB"
        },
        "contents": "Hello, Bob!"
      }
    }
""".trimIndent()

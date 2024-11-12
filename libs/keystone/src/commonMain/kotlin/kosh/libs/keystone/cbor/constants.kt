package kosh.libs.keystone.cbor

internal const val TYPE_UNSIGNED_INTEGER = 0x00u
internal const val TYPE_NEGATIVE_INTEGER = 0x01u
internal const val TYPE_BYTE_STRING = 0x02u
internal const val TYPE_TEXT_STRING = 0x03u
internal const val TYPE_ARRAY = 0x04u
internal const val TYPE_MAP = 0x05u
internal const val TYPE_TAG = 0x06u
internal const val TYPE_FLOAT_SIMPLE = 0x07u

internal const val ONE_BYTE = 0x18u
internal const val TWO_BYTES = 0x19u
internal const val FOUR_BYTES = 0x1au
internal const val EIGHT_BYTES = 0x1bu

internal const val FALSE = 0x14u
internal const val TRUE = 0x15u
internal const val NULL = 0x16u
internal const val UNDEFINED = 0x17u

internal const val HALF_PRECISION_FLOAT = 0x19u
internal const val SINGLE_PRECISION_FLOAT = 0x1au
internal const val DOUBLE_PRECISION_FLOAT = 0x1bu
internal const val BREAK = 0x1fu

internal const val TAG_STANDARD_DATE_TIME = 0
internal const val TAG_EPOCH_DATE_TIME = 1
internal const val TAG_POSITIVE_BIGINT = 2
internal const val TAG_NEGATIVE_BIGINT = 3
internal const val TAG_DECIMAL_FRACTION = 4
internal const val TAG_BIGDECIMAL = 5
internal const val TAG_EXPECTED_BASE64_URL_ENCODED = 21
internal const val TAG_EXPECTED_BASE64_ENCODED = 22
internal const val TAG_EXPECTED_BASE16_ENCODED = 23
internal const val TAG_CBOR_ENCODED = 24
internal const val TAG_URI = 32
internal const val TAG_BASE64_URL_ENCODED = 33
internal const val TAG_BASE64_ENCODED = 34
internal const val TAG_REGEXP = 35
internal const val TAG_MIME_MESSAGE = 36
internal const val TAG_CBOR_MARKER = 55799

package kosh.libs.ipfs.cbor

internal enum class Tag {
    TYPE_UNSIGNED_INTEGER,
    TYPE_NEGATIVE_INTEGER,
    TYPE_BYTE_STRING,
    TYPE_TEXT_STRING,
    TYPE_ARRAY,
    TYPE_MAP,
    TYPE_TAG,
    TYPE_FLOAT_SIMPLE,
}

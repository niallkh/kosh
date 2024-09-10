package kosh.domain.utils

import kosh.domain.models.ByteString

fun ByteString?.orEmpty() = this ?: ByteString()

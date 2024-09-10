@file:OptIn(ExperimentalNativeApi::class)

package kosh.domain.core

import kotlin.experimental.ExperimentalNativeApi

actual fun assert(condition: Boolean) {
    kotlin.assert(condition)
}

actual inline fun assert(condition: Boolean, message: () -> String) {
    kotlin.assert(condition, message)
}

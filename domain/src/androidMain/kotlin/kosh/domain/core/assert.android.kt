package kosh.domain.core

actual fun assert(condition: Boolean) {
    kotlin.assert(condition)
}

actual inline fun assert(condition: Boolean, message: () -> String) {
    kotlin.assert(condition, message)
}

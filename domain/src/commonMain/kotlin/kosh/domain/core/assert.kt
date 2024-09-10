package kosh.domain.core

expect fun assert(condition: Boolean)

expect inline fun assert(condition: Boolean, message: () -> String)

package kosh.libs.reown

sealed class ReownResult<out T> {
    data class Failure<T>(val value: ReownFailure) : ReownResult<T>()
    data class Success<T>(val value: T) : ReownResult<T>() {

        companion object {
            private val Unit = Success(kotlin.Unit)
            operator fun invoke() = Unit
        }
    }
}

package kosh.domain.core

inline fun <T> provider(
    crossinline factory: () -> T,
): Lazy<T> = lazy(LazyThreadSafetyMode.NONE) {
    factory()
}

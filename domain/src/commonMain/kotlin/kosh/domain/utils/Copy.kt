package kosh.domain.utils

import arrow.optics.Getter
import arrow.optics.OptionalGetter
import arrow.optics.Setter
import arrow.optics.Traversal

interface Copy<A> {

    infix fun <B> Setter<A, B>.set(b: B)

    infix fun <B> Traversal<A, B>.transform(f: (B) -> B)

    fun <B> Getter<A, B>.get(): B

    fun <B> OptionalGetter<A, B>.getOrNull(): B?

    fun <B> inside(field: Traversal<A, B>, f: Copy<B>.() -> Unit): Unit =
        field.transform { it.copy(f) }
}


class CopyImpl<A>(
    var current: A,
) : Copy<A> {
    override fun <B> Setter<A, B>.set(b: B) {
        current = set(current, b)
    }

    override fun <B> Traversal<A, B>.transform(f: (B) -> B) {
        current = modify(current, f)
    }

    override fun <B> Getter<A, B>.get(): B = get(current)

    override fun <B> OptionalGetter<A, B>.getOrNull(): B? = getOrNull(current)
}

inline fun <A> A.copy(f: Copy<A>.() -> Unit): A = CopyImpl(this).also(f).current

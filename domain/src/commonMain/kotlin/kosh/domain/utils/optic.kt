package kosh.domain.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SnapshotMutationPolicy
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.Snapshot
import androidx.compose.runtime.structuralEqualityPolicy
import arrow.optics.Getter
import arrow.optics.Lens
import kosh.domain.repositories.NIL
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.StateFlow

fun <T, A> StateFlow<T>.optic(g: Getter<T, A>): StateFlow<A> = object : StateFlow<A> {
    override val value: A
        get() = g.get(this@optic.value)

    override suspend fun collect(collector: FlowCollector<A>): Nothing {
        var last: Any? = NIL
        this@optic.collect {
            val new = g.get(it)
            if (new != last) {
                last = new
                collector.emit(new)
            } else {
                last = new
            }
        }
    }

    override val replayCache: List<A>
        get() = this@optic.replayCache.map { g.get(it) }
}

fun <T, A> Getter<T, A?>?.orNull(): Getter<T, A?> = this ?: Getter { null }

@Composable
fun <T, A> State<T>.optic(
    g: Getter<T, A>,
    policy: SnapshotMutationPolicy<A> = structuralEqualityPolicy(),
): State<A> = remember(this, g) {
    object : State<A> {
        val derived = derivedStateOf(policy) { g.get(this@optic.value) }
        override val value: A
            get() = derived.value
    }
}

@Composable
fun <T, A> MutableState<T>.optic(
    lens: Lens<T, A>,
    policy: SnapshotMutationPolicy<A> = structuralEqualityPolicy(),
): MutableState<A> = remember(this) {
    object : MutableState<A> {
        val derived = derivedStateOf(policy) { lens.get(this@optic.value) }

        override var value: A
            get() = derived.value
            set(newValue) {
                Snapshot.withMutableSnapshot {
                    val prevValue = derived.value
                    if (!policy.equivalent(prevValue, newValue)) {
                        this@optic.value = lens.set(this@optic.value, newValue)
                    }
                }
            }

        override fun component1(): A = value
        override fun component2(): (A) -> Unit = { value = it }
    }
}

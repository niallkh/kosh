package kosh.domain.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SnapshotMutationPolicy
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshots.Snapshot
import androidx.compose.runtime.structuralEqualityPolicy
import arrow.optics.Getter
import arrow.optics.Lens
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

@Composable
fun <T, A> State<T>.optic(
    getter: Getter<T, A>,
    policy: SnapshotMutationPolicy<A> = structuralEqualityPolicy(),
): State<A> {
    val currentGetter by rememberUpdatedState(getter)
    return remember(this) {
        derivedStateOf(policy) { currentGetter.get(this@optic.value) }
    }
}

@Composable
fun <T, A> optic(
    getter: Getter<T, A>,
    policy: SnapshotMutationPolicy<A> = structuralEqualityPolicy(),
    calculation: () -> T,
): State<A> {
    val currentGetter by rememberUpdatedState(getter)
    return remember {
        derivedStateOf(policy) { currentGetter.get(calculation()) }
    }
}

@Composable
fun <T, A> MutableState<T>.optic(
    lens: Lens<T, A>,
    policy: SnapshotMutationPolicy<A> = structuralEqualityPolicy(),
): MutableState<A> {
    val currentLens by rememberUpdatedState(lens)

    return remember(this) {
        object : MutableState<A> {
            val derived = derivedStateOf(policy) { currentLens.get(this@optic.value) }

            override var value: A
                get() = derived.value
                set(newValue) {
                    Snapshot.withMutableSnapshot {
                        val prevValue = derived.value
                        if (!policy.equivalent(prevValue, newValue)) {
                            this@optic.value = currentLens.set(this@optic.value, newValue)
                        }
                    }
                }

            override fun component1(): A = value
            override fun component2(): (A) -> Unit = { value = it }
        }
    }
}

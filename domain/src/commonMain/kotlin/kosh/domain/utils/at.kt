package kosh.domain.utils

import arrow.optics.PLens
import arrow.optics.typeclasses.At
import kosh.domain.serializers.ImmutableList
import kosh.domain.serializers.PersistentHashMap
import kosh.domain.serializers.PersistentHashSet
import kosh.domain.serializers.PersistentSet
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.toPersistentHashMap
import kotlinx.collections.immutable.toPersistentHashSet
import kotlinx.collections.immutable.toPersistentList
import kotlinx.collections.immutable.toPersistentMap
import kotlinx.collections.immutable.toPersistentSet

fun <K, V> At.Companion.pmap(): At<PersistentMap<K, V>, K, V?> = At { i ->
    PLens(
        get = { it[i] },
        set = { map, v ->
            if (v == null) {
                map.toPersistentMap().remove(i)
            } else {
                map.toPersistentMap().put(i, v)
            }
        }
    )
}

fun <K, V> At.Companion.phmap(): At<PersistentHashMap<K, V>, K, V?> = At { i ->
    PLens(
        get = { it[i] },
        set = { map, v ->
            if (v == null) {
                map.toPersistentHashMap().remove(i)
            } else {
                map.toPersistentHashMap().put(i, v)
            }
        }
    )
}

fun <V> At.Companion.pset(): At<PersistentSet<V>, V, Boolean> = At { i ->
    PLens(
        get = { i in it },
        set = { s, b ->
            if (b) s.toPersistentSet().add(i)
            else s.toPersistentSet().remove(i)
        }
    )
}

fun <V> At.Companion.phset(): At<PersistentHashSet<V>, V, Boolean> = At { i ->
    PLens(
        get = { i in it },
        set = { s, b ->
            if (b) s.toPersistentHashSet().add(i)
            else s.toPersistentHashSet().remove(i)
        }
    )
}

fun <V> At.Companion.ilist(): At<ImmutableList<V>, V, Boolean> = At { i ->
    PLens(
        get = { i in it },
        set = { s, b ->
            if (b) s.toPersistentList().add(i)
            else s.toPersistentList().remove(i)
        }
    )
}

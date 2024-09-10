package kosh.domain.utils

import arrow.core.left
import arrow.core.right
import arrow.optics.POptional
import arrow.optics.typeclasses.Index
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.toPersistentHashMap
import kotlinx.collections.immutable.toPersistentMap


fun <K, V> Index.Companion.pmap(): Index<PersistentMap<K, V>, K, V> = Index { i ->
    POptional(
        getOrModify = { it[i]?.right() ?: it.left() },
        set = { map, v ->
            if (v == null) {
                map.toPersistentMap().remove(i)
            } else {
                map.toPersistentMap().put(i, v)
            }
        }
    )
}

fun <K, V> Index.Companion.phmap(): Index<PersistentMap<K, V>, K, V> = Index { i ->
    POptional(
        getOrModify = { it[i]?.right() ?: it.left() },
        set = { map, v ->
            if (v == null) {
                map.toPersistentMap().remove(i)
            } else {
                map.toPersistentHashMap().put(i, v)
            }
        }
    )
}


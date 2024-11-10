package kosh.data

import kosh.data.sources.KeyValueStore
import kosh.libs.keystore.KeyStore

interface DataComponent {
    val keyValueStore: KeyValueStore
    val keyStore: KeyStore
}

package kosh.data

import kosh.data.sources.AppStateSource
import kosh.libs.keystore.KeyStore

interface DataComponent {
    val appStateSource: AppStateSource
    val keyStore: KeyStore
}

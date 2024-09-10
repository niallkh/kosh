package kosh.app.di

import kotlinx.coroutines.CoroutineScope

interface CoroutinesComponent {

    val applicationScope: CoroutineScope
}

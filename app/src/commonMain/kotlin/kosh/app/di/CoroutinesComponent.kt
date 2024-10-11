package kosh.app.di

import kotlinx.coroutines.CoroutineScope

public interface CoroutinesComponent {

    public val applicationScope: CoroutineScope
}

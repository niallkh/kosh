package kosh.app.di

import kosh.app.BuildConfig
import kosh.data.reown.ReownComponent
import kosh.domain.core.provider
import kosh.libs.reown.AndroidReownAdapter
import kosh.libs.reown.ReownAdapter

class AndroidReownComponent(
    coroutinesComponent: CoroutinesComponent,
    androidComponent: AndroidComponent,
) : ReownComponent,
    AndroidComponent by androidComponent,
    CoroutinesComponent by coroutinesComponent {

    override val reownAdapter: ReownAdapter by provider {
        AndroidReownAdapter(
            context = context,
            projectId = BuildConfig.REOWN_PROJECT,
            applicationScope = applicationScope
        )
    }
}
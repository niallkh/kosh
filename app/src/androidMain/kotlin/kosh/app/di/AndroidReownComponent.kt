package kosh.app.di

import kosh.data.reown.ReownComponent
import kosh.domain.core.provider
import kosh.libs.reown.AndroidReownAdapter
import kosh.libs.reown.ReownAdapter

internal class AndroidReownComponent(
    coroutinesComponent: CoroutinesComponent,
    androidComponent: AndroidComponent,
    appComponent: AppComponent,
) : ReownComponent,
    AndroidComponent by androidComponent,
    CoroutinesComponent by coroutinesComponent {

    override val reownAdapter: ReownAdapter by provider {
        AndroidReownAdapter(
            context = context,
            projectId = appComponent.reownProject,
            applicationScope = applicationScope
        )
    }
}

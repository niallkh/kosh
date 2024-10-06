package kosh.app.di.impl

import kosh.app.BuildConfig
import kosh.app.di.AndroidComponent
import kosh.app.di.CoroutinesComponent
import kosh.data.wc2.ReownComponent
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
            projectId = BuildConfig.WC_PROJECT,
            applicationScope = applicationScope
        )
    }
}

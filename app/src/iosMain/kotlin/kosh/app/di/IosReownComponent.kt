package kosh.app.di

import kosh.data.reown.ReownComponent
import kosh.libs.reown.ReownAdapter

class IosReownComponent(
    override val reownAdapter: ReownAdapter,
) : ReownComponent

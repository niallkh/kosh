package kosh.presentation.core

import co.touchlab.kermit.Logger
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ComponentContextFactory
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import com.arkivanov.essenty.instancekeeper.InstanceKeeperOwner
import com.arkivanov.essenty.lifecycle.LifecycleOwner
import com.arkivanov.essenty.statekeeper.StateKeeperOwner
import kosh.presentation.di.PresentationScope

class DefaultPresentationContext(
    componentContext: ComponentContext,
    override val logger: Logger,
    override val presentationScope: PresentationScope,
    override val container: MutableMap<String, Any>,
) : PresentationContext,
    LifecycleOwner by componentContext,
    StateKeeperOwner by componentContext,
    InstanceKeeperOwner by componentContext,
    BackHandlerOwner by componentContext {

    override val componentContextFactory: ComponentContextFactory<PresentationContext> =
        ComponentContextFactory { lifecycle, stateKeeper, instanceKeeper, backHandler ->
            val ctx = componentContext.componentContextFactory(
                lifecycle = lifecycle,
                stateKeeper = stateKeeper,
                instanceKeeper = instanceKeeper,
                backHandler = backHandler
            )

            DefaultPresentationContext(
                componentContext = ctx,
                logger = logger,
                presentationScope = presentationScope.create(),
                container = mutableMapOf()
            )
        }
}

fun defaultPresentationContext(
    componentContext: ComponentContext,
    presentationScope: PresentationScope,
): PresentationContext = DefaultPresentationContext(
    componentContext = componentContext,
    logger = Logger.withTag("[K]PresentationContext"),
    presentationScope = presentationScope,
    container = mutableMapOf()
)

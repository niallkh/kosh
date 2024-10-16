package kosh.presentation.core

import co.touchlab.kermit.Logger
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ComponentContextFactory
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import com.arkivanov.essenty.instancekeeper.InstanceKeeperOwner
import com.arkivanov.essenty.lifecycle.LifecycleOwner
import com.arkivanov.essenty.statekeeper.StateKeeperOwner
import kosh.presentation.di.UiScope

class DefaultUiContext(
    componentContext: ComponentContext,
    override val logger: Logger,
    override val uiScope: UiScope,
    override val container: MutableMap<String, Any>,
) : UiContext,
    LifecycleOwner by componentContext,
    StateKeeperOwner by componentContext,
    InstanceKeeperOwner by componentContext,
    BackHandlerOwner by componentContext {

    override val componentContextFactory: ComponentContextFactory<UiContext> =
        ComponentContextFactory { lifecycle, stateKeeper, instanceKeeper, backHandler ->
            val ctx = componentContext.componentContextFactory(
                lifecycle = lifecycle,
                stateKeeper = stateKeeper,
                instanceKeeper = instanceKeeper,
                backHandler = backHandler
            )

            DefaultUiContext(
                componentContext = ctx,
                logger = logger,
                uiScope = uiScope.create(),
                container = mutableMapOf()
            )
        }
}

fun defaultUiContext(
    componentContext: ComponentContext,
    uiScope: UiScope,
): UiContext = DefaultUiContext(
    componentContext = componentContext,
    logger = Logger.withTag("[K]UiContext"),
    uiScope = uiScope,
    container = mutableMapOf()
)

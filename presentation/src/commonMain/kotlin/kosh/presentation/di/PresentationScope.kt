package kosh.presentation.di

import kosh.domain.AppRepositoriesComponent
import kosh.domain.DomainComponent
import kosh.domain.UiRepositoriesComponent
import kotlinx.serialization.cbor.Cbor

interface PresentationScope {

    val cbor: Cbor

    val domain: DomainComponent

    val appRepositoriesComponent: AppRepositoriesComponent

    val uiRepositoriesComponent: UiRepositoriesComponent

    val deeplinkHandler: DeeplinkHandler

    fun create(): PresentationScope
}

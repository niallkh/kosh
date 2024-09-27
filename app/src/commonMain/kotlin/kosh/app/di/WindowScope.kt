package kosh.app.di

import kosh.domain.WindowRepositoriesComponent
import kosh.presentation.di.RouteScopeFactory

interface WindowScope {

    val routeScopeFactory: RouteScopeFactory

    val windowRepositoriesComponent: WindowRepositoriesComponent
}

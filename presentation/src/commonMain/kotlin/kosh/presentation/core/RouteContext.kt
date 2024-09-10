package kosh.presentation.core

import kotlin.reflect.KType

interface RouteContext : AppContext, MutableMap<KType, Any>

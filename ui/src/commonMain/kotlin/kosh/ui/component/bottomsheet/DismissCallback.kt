package kosh.ui.component.bottomsheet

fun interface DismissCallback {
    operator fun invoke(onFinish: () -> Unit)
}

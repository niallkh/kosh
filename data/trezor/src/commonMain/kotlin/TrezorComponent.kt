package kosh.data.trezor

import kosh.libs.trezor.TrezorManager

interface TrezorComponent {
    val trezorManager: TrezorManager
}

package kosh.app.di.impl

import kosh.app.di.TransportComponent
import kosh.data.keystone.KeystoneComponent
import kosh.domain.core.provider
import kosh.libs.keystone.DefaultKeystoneManager
import kosh.libs.keystone.KeystoneManager

internal class DefaultKeystoneComponent(
    transportComponent: TransportComponent,
) : KeystoneComponent, TransportComponent by transportComponent {

    override val keystoneManager: KeystoneManager by provider {
        DefaultKeystoneManager(
            usb = usb,
        )
    }
}

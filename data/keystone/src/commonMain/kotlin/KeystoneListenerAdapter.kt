package kosh.data.keystone

import kosh.domain.repositories.KeystoneListener
import kosh.libs.keystone.KeystoneManager

internal class KeystoneListenerAdapter(
    private val keystoneListener: KeystoneListener,
) : KeystoneManager.Connection.Listener

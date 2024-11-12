package kosh.domain.usecases.keystone

import kosh.domain.models.hw.Keystone
import kosh.domain.repositories.KeystoneRepo
import kosh.domain.serializers.ImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlin.time.Duration.Companion.milliseconds

class DefaultKeystoneService(
    private val keystoneRepo: KeystoneRepo,
) : KeystoneService {

    override fun list(): Flow<ImmutableList<Keystone>> = keystoneRepo.list
        .distinctUntilChanged()
        .debounce(300.milliseconds)
}

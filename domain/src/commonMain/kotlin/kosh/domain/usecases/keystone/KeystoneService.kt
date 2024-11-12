package kosh.domain.usecases.keystone

import kosh.domain.models.hw.Keystone
import kosh.domain.serializers.ImmutableList
import kotlinx.coroutines.flow.Flow

interface KeystoneService {

    fun list(): Flow<ImmutableList<Keystone>>
}

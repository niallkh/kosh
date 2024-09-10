package kosh.presentation.core

import arrow.atomic.Atomic
import arrow.atomic.getAndUpdate
import arrow.core.continuations.AtomicRef
import arrow.core.continuations.getAndUpdate
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.Job
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.currentCoroutineContext

interface CompositeJob {
    val isActive: Boolean
    suspend fun bind()
    fun cancel()
}

private val Initial = Job().apply { complete() }
private val Canceled = Job().apply { complete() }

class ListCompositeJob : CompositeJob {

    private val jobsRef = AtomicRef(persistentListOf<Job>(Initial))

    override val isActive: Boolean
        get() = jobsRef.get().all { it.isActive }

    override suspend fun bind() {
        val currentJob = checkNotNull(currentCoroutineContext()[Job])
        val previous = jobsRef.getAndUpdate { jobs ->
            if (jobs.firstOrNull() == Canceled) {
                jobs
            } else {
                jobs.removeAll { it.isActive.not() }.add(currentJob)
            }
        }

        if (previous.firstOrNull() == Canceled) {
            currentJob.cancel()
            awaitCancellation()
        }
    }

    override fun cancel() {
        val jobs = jobsRef.getAndUpdate { persistentListOf(Canceled) }
        jobs.forEach { it.cancel() }
    }
}

class SingleCompositeJob : CompositeJob {

    private val jobRef = Atomic<Job>(Initial)

    override val isActive: Boolean
        get() = jobRef.get().isActive

    override suspend fun bind() {
        val currentJob = checkNotNull(currentCoroutineContext()[Job])

        val previousJob = jobRef.getAndUpdate {
            if (it === Canceled) it else currentJob
        }

        if (previousJob === Canceled) {
            currentJob.cancel()
            awaitCancellation()
        } else {
            previousJob.cancelAndJoin()
        }
    }

    override fun cancel() {
        jobRef.getAndUpdate { Canceled }.cancel()
    }
}

class QueueCompositeJob : CompositeJob {

    private val jobRef = Atomic<Job>(Initial)

    override val isActive: Boolean
        get() = jobRef.get().isActive

    override suspend fun bind() {
        val currentJob = checkNotNull(currentCoroutineContext()[Job])

        val previousJob = jobRef.getAndUpdate {
            if (it === Canceled) it else currentJob
        }

        if (previousJob === Canceled) {
            currentJob.cancel()
            awaitCancellation()
        } else {
            previousJob.join()
        }
    }

    override fun cancel() {
        jobRef.getAndUpdate { Canceled }.cancel()
    }
}

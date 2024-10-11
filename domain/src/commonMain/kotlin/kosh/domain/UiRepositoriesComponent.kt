package kosh.domain

import kosh.domain.repositories.ShareRepo

interface UiRepositoriesComponent {
    val shareRepo: ShareRepo
        get() = throw NotImplementedError("Share Repo not available")
}

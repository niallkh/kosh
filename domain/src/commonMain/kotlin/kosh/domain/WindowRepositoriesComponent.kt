package kosh.domain

import kosh.domain.repositories.ShareRepo

interface WindowRepositoriesComponent {
    val shareRepo: ShareRepo
        get() = throw NotImplementedError("Share Repo not available")
}

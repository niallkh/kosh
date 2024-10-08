package kosh.app.di

import android.content.ContentResolver
import androidx.activity.result.ActivityResultRegistry
import kosh.data.AndroidShareRepo
import kosh.domain.WindowRepositoriesComponent
import kosh.domain.core.provider
import kosh.domain.repositories.ShareRepo


class AndroidWindowRepositoriesComponent(
    contentResolver: ContentResolver,
    activityResultRegistry: ActivityResultRegistry,
) : WindowRepositoriesComponent {

    override val shareRepo: ShareRepo by provider {
        AndroidShareRepo(
            contentResolver = contentResolver,
            activityResultRegistry = activityResultRegistry,
        )
    }
}

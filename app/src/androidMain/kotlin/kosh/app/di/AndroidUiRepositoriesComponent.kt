package kosh.app.di

import android.content.ContentResolver
import androidx.activity.result.ActivityResultRegistry
import kosh.data.AndroidShareRepo
import kosh.domain.UiRepositoriesComponent
import kosh.domain.core.provider
import kosh.domain.repositories.ShareRepo


internal class AndroidUiRepositoriesComponent(
    contentResolver: ContentResolver,
    activityResultRegistry: ActivityResultRegistry,
) : UiRepositoriesComponent {

    override val shareRepo: ShareRepo by provider {
        AndroidShareRepo(
            contentResolver = contentResolver,
            activityResultRegistry = activityResultRegistry,
        )
    }
}

package kosh.app.di.impl

import kosh.app.di.FileSystemComponent
import kosh.app.di.FilesComponent
import kotlinx.io.files.Path

internal class DefaultFilesComponent(
    fileSystemComponent: FileSystemComponent,
) : FilesComponent,
    FileSystemComponent by fileSystemComponent {

    override val appDataStorePath: () -> Path = {
        Path(noBackupFilesDir(), "app_state")
    }

    override val keyStorePath: () -> Path = {
        Path(noBackupFilesDir(), "keystore.preferences")
    }

    override val keyValuePath: () -> Path = {
        Path(noBackupFilesDir(), "kvstore")
    }

    override val httpPath: () -> Path = {
        Path(cacheDir(), "http")
    }

    override val imagePath: () -> Path = {
        Path(cacheDir(), "img")
    }
}

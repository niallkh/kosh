package kosh.app.di.impl

import arrow.core.memoize
import kosh.app.di.FileSystemComponent
import kosh.app.di.FilesComponent
import kotlinx.io.files.Path

internal class DefaultFilesComponent(
    fileSystemComponent: FileSystemComponent,
) : FilesComponent,
    FileSystemComponent by fileSystemComponent {

    override val appDataStorePath: () -> Path = {
        Path(noBackupFilesDir(), "app_state.pb")
    }.memoize()

    override val keyStorePath: () -> Path = {
        Path(noBackupFilesDir(), "keystore.preferences_pb")
    }.memoize()

    override val fileRepoPath: () -> Path = {
        Path(noBackupFilesDir(), "file_repo")
    }.memoize()

    override val httpPath: () -> Path = {
        Path(cacheDir(), "http")
    }.memoize()

    override val imagePath: () -> Path = {
        Path(cacheDir(), "img")
    }.memoize()
}

package kosh.app.di.impl

import arrow.core.memoize
import kosh.app.di.FileSystemComponent
import kosh.app.di.FilesComponent
import okio.Path

class DefaultFilesComponent(
    fileSystemComponent: FileSystemComponent,
) : FilesComponent,
    FileSystemComponent by fileSystemComponent {
    override val appDataStorePath: () -> Path = {
        noBackupFilesDir().resolve("app_state.pb")
    }.memoize()
    override val keyStorePath: () -> Path = {
        noBackupFilesDir().resolve("keystore.preferences_pb")
    }.memoize()
    override val fileRepoPath: () -> Path = {
        noBackupFilesDir().resolve("file_repo")
    }.memoize()
    override val httpPath: () -> Path = {
        cacheDir().resolve("http")
    }.memoize()
    override val imagePath: () -> Path = {
        cacheDir().resolve("img")
    }.memoize()
}

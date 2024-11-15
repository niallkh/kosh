package kosh.app.di

import kotlinx.io.files.FileSystem
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem

internal class AndroidFileSystemComponent(
    private val androidComponent: AndroidComponent,
) : FileSystemComponent {

    override val filesDir: () -> Path = {
        Path(androidComponent.context.filesDir.toString())
    }

    override val cacheDir: () -> Path = {
        Path(androidComponent.context.cacheDir.toString())
    }

    override val noBackupFilesDir: () -> Path = {
        Path(androidComponent.context.noBackupFilesDir.toString())
    }

    override val fileSystem: FileSystem
        get() = SystemFileSystem
}

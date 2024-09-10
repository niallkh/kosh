package kosh.app.di.impl

import arrow.core.memoize
import kosh.app.di.AndroidComponent
import kosh.app.di.FileSystemComponent
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toOkioPath

class AndroidFileSystemComponent(
    private val androidComponent: AndroidComponent,
) : FileSystemComponent {
    override val filesDir: () -> Path = {
        androidComponent.context.filesDir.toOkioPath()
    }.memoize()
    override val cacheDir: () -> Path = {
        androidComponent.context.cacheDir.toOkioPath()
    }.memoize()
    override val noBackupFilesDir: () -> Path = {
        androidComponent.context.noBackupFilesDir.toOkioPath()
    }.memoize()

    override val fileSystem: FileSystem
        get() = FileSystem.SYSTEM
}

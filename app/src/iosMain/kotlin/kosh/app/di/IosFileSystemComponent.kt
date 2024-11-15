package kosh.app.di

import kotlinx.io.files.FileSystem
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import platform.Foundation.NSCachesDirectory
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSUserDomainMask

internal class IosFileSystemComponent : FileSystemComponent {
    override val filesDir: () -> Path = {
        Path(
            NSSearchPathForDirectoriesInDomains(
                NSDocumentDirectory,
                NSUserDomainMask,
                true
            ).first() as String
        )
    }

    override val cacheDir: () -> Path = {
        Path(
            NSSearchPathForDirectoriesInDomains(
                NSCachesDirectory,
                NSUserDomainMask,
                true
            ).first() as String
        )
    }

    override val noBackupFilesDir: () -> Path = {
        val dir = NSSearchPathForDirectoriesInDomains(
            NSDocumentDirectory,
            NSUserDomainMask,
            true
        ).first() as String

//        NSURL(dir).setResourceValue(true, forKey = NSURLIsExcludedFromBackupKey, error = null)
        Path(dir)
    }

    override val fileSystem: FileSystem
        get() = SystemFileSystem
}

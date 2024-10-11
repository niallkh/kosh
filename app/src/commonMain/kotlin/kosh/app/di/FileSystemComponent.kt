package kosh.app.di

import kotlinx.io.files.FileSystem
import kotlinx.io.files.Path

public interface FileSystemComponent {
    public val filesDir: () -> Path
    public val cacheDir: () -> Path
    public val noBackupFilesDir: () -> Path
    public val fileSystem: FileSystem
}


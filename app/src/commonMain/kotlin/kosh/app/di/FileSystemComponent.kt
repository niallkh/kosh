package kosh.app.di

import okio.FileSystem
import okio.Path

interface FileSystemComponent {
    val filesDir: () -> Path
    val cacheDir: () -> Path
    val noBackupFilesDir: () -> Path
    val fileSystem: FileSystem
}


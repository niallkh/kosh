package kosh.app.di

import kotlinx.io.files.FileSystem
import kotlinx.io.files.Path

interface FileSystemComponent {
    val filesDir: () -> Path
    val cacheDir: () -> Path
    val noBackupFilesDir: () -> Path
    val fileSystem: FileSystem
}


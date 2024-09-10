package kosh.app.di

import okio.FileSystem
import okio.Path

interface FilesComponent {
    val appDataStorePath: () -> Path
    val keyStorePath: () -> Path
    val fileRepoPath: () -> Path
    val httpPath: () -> Path
    val imagePath: () -> Path
    val fileSystem: FileSystem
}

package kosh.app.di

import kotlinx.io.files.FileSystem
import kotlinx.io.files.Path


interface FilesComponent {
    val appDataStorePath: () -> Path
    val keyStorePath: () -> Path
    val fileRepoPath: () -> Path
    val httpPath: () -> Path
    val imagePath: () -> Path
    val fileSystem: FileSystem
}

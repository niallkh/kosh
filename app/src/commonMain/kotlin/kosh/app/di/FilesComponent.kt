package kosh.app.di

import kotlinx.io.files.FileSystem
import kotlinx.io.files.Path


public interface FilesComponent {
    public val appDataStorePath: () -> Path
    public val keyStorePath: () -> Path
    public val fileRepoPath: () -> Path
    public val httpPath: () -> Path
    public val imagePath: () -> Path
    public val fileSystem: FileSystem
}

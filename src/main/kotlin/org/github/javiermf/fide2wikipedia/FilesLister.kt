package org.github.javiermf.fide2wikipedia

import java.io.File

class FilesLister (dirPath: String)
{
    fun getRatingFileForStyle(gameStyle: GameStyle): File
        = ratingsFiles.first { it.gameStyle == gameStyle }.file

    private val ratingsFiles: Set<FideRatingsFile>
    init {
        val dirFolder = File(dirPath)
        if (!dirFolder.exists()) throw RuntimeException("Path not found $dirPath")
        if (!dirFolder.isDirectory) throw RuntimeException("$dirPath must a directory")
        ratingsFiles = dirFolder.listFiles()?.mapNotNull { file ->
            if (!file.name.endsWith(".xml")) null else
            when {
                file.name.contains("standard") -> FideRatingsFile(file, GameStyle.OPEN)
                file.name.contains("rapid") -> FideRatingsFile(file, GameStyle.RAPID)
                file.name.contains("blitz") -> FideRatingsFile(file, GameStyle.BULLET)
                else -> null
            }
        }?.toSet() ?: emptySet()

        if (ratingsFiles.size != 3)
            throw RuntimeException("Some rating file are missing. Only ${ratingsFiles.size} found.")
    }
}

data class FideRatingsFile(
    val file: File,
    val gameStyle: GameStyle
)

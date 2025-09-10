package org.github.javiermf.fide2wikipedia.infrastructure.filereader

import org.github.javiermf.fide2wikipedia.domain.model.GameStyle
import java.io.File

class FilesLister(dirPath: String) {


    private val ratingsFiles: Set<FideRatingsFile>

    init {
        val dirFolder = File(dirPath)
        if (!dirFolder.exists()) throw RuntimeException("Path not found $dirPath")
        if (!dirFolder.isDirectory) throw RuntimeException("$dirPath must be a directory")
        ratingsFiles = dirFolder.listFiles()
            ?.filter { it.name.endsWith(".xml") }
            ?.map { file ->
                when {
                    file.isStandard() -> FideRatingsFile(file, GameStyle.OPEN)
                    file.isRapid() -> FideRatingsFile(file, GameStyle.RAPID)
                    file.isBlitz() -> FideRatingsFile(file, GameStyle.BULLET)
                    else -> throw RuntimeException(
                        "Unknown file"
                    )
                }
            }?.toSet() ?: emptySet()

        if (ratingsFiles.size != 3)
            throw RuntimeException("Some rating file are missing. Only ${ratingsFiles.size} found.")
    }

    fun getRatingFileForStyle(gameStyle: GameStyle): File = ratingsFiles.first { it.gameStyle == gameStyle }.file

    private fun File.isStandard() = this.name.contains("standard")
    private fun File.isRapid() = this.name.contains("rapid")
    private fun File.isBlitz() = this.name.contains("blitz")
}

data class FideRatingsFile(
    val file: File,
    val gameStyle: GameStyle
)

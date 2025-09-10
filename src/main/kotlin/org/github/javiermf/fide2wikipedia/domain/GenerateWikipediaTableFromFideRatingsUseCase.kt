package org.github.javiermf.fide2wikipedia.domain

import org.github.javiermf.fide2wikipedia.domain.model.GameStyle
import org.github.javiermf.fide2wikipedia.domain.service.FideDataReader
import org.github.javiermf.fide2wikipedia.domain.service.NameMapper
import org.github.javiermf.fide2wikipedia.domain.service.OutputFileWriter
import org.github.javiermf.fide2wikipedia.domain.service.wikipedia.WikipediaTableWriter
import org.github.javiermf.fide2wikipedia.infrastructure.filereader.FilesLister

class GenerateWikipediaTableFromFideRatingsUseCase(
    private val dirWithRatingFiles: String,
    private val fideDataReader: FideDataReader,
    private val outputWriter: OutputFileWriter
){

    fun generate() {
        val fileLister = FilesLister(dirWithRatingFiles)

        val wikiGameStyleWriter = WikipediaTableWriter(NameMapper(), outputWriter)

        listOf(GameStyle.OPEN, GameStyle.RAPID, GameStyle.BULLET)
            .forEach  { gameStyle ->
                val ratingsFile = fileLister.getRatingFileForStyle(gameStyle)
                val players = fideDataReader.readFideData(ratingsFile.absolutePath)
                wikiGameStyleWriter.writeGameStyleSection(gameStyle, players)
            }
    }
}

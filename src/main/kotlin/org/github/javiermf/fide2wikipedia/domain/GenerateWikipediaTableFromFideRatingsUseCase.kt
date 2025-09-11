package org.github.javiermf.fide2wikipedia.domain

import org.github.javiermf.fide2wikipedia.domain.model.GameStyle
import org.github.javiermf.fide2wikipedia.domain.service.OutputFileWriter
import org.github.javiermf.fide2wikipedia.domain.service.wikipedia.WikipediaTableWriter
import org.github.javiermf.fide2wikipedia.infrastructure.downloader.FideFilesDownloader
import org.github.javiermf.fide2wikipedia.infrastructure.filereader.FideDataXMLReader
import org.github.javiermf.fide2wikipedia.infrastructure.filereader.FilesLister
import org.github.javiermf.fide2wikipedia.infrastructure.filereader.GameStylePlayersRetrieverLocal
import java.time.Clock

class GenerateWikipediaTableFromFideRatingsUseCase(
    private val downloader: FideFilesDownloader,
    private val outputWriter: OutputFileWriter,
    private val clock: Clock
){

    fun generate() {
        val folderPath = downloader.downloadAndUnzipFideFiles()
        val playersRetriever = GameStylePlayersRetrieverLocal(
            filesLister = FilesLister(folderPath),
            fideDataXMLReader = FideDataXMLReader()
        )

        val wikiGameStyleWriter = WikipediaTableWriter(outputWriter, clock)

        listOf(GameStyle.OPEN, GameStyle.RAPID, GameStyle.BULLET)
            .forEach  { gameStyle ->
                val players = playersRetriever.getPlayersListForGameStyle(gameStyle)
                          wikiGameStyleWriter.writeGameStyleSection(gameStyle, players)
            }
    }
}

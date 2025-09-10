package org.github.javiermf.fide2wikipedia.domain

import org.github.javiermf.fide2wikipedia.domain.model.GameStyle
import org.github.javiermf.fide2wikipedia.domain.service.GameStylePlayersRetriever
import org.github.javiermf.fide2wikipedia.domain.service.OutputFileWriter
import org.github.javiermf.fide2wikipedia.domain.service.wikipedia.WikipediaTableWriter
import java.time.Clock

class GenerateWikipediaTableFromFideRatingsUseCase(
    private val playersRetriever: GameStylePlayersRetriever,
    private val outputWriter: OutputFileWriter,
    private val clock: Clock
){

    fun generate() {
        val wikiGameStyleWriter = WikipediaTableWriter(outputWriter, clock)

        listOf(GameStyle.OPEN, GameStyle.RAPID, GameStyle.BULLET)
            .forEach  { gameStyle ->
                val players = playersRetriever.getPlayersListForGameStyle(gameStyle)
                          wikiGameStyleWriter.writeGameStyleSection(gameStyle, players)
            }
    }
}

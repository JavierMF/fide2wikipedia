package org.github.javiermf.fide2wikipedia.infrastructure.filereader

import org.github.javiermf.fide2wikipedia.domain.model.GameStyle
import org.github.javiermf.fide2wikipedia.domain.model.Player
import org.github.javiermf.fide2wikipedia.domain.service.GameStylePlayersRetriever

class GameStylePlayersRetrieverLocal(
    private val filesLister: FilesLister,
    private val fideDataXMLReader: FideDataXMLReader
): GameStylePlayersRetriever {
    override fun getPlayersListForGameStyle(gameStyle: GameStyle): List<Player> {
        val ratingsFile = filesLister.getRatingFileForStyle(gameStyle)
        return fideDataXMLReader.readFideData(ratingsFile.absolutePath)
    }
}

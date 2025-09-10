package org.github.javiermf.fide2wikipedia.domain.service

import org.github.javiermf.fide2wikipedia.domain.model.GameStyle
import org.github.javiermf.fide2wikipedia.domain.model.Player

interface GameStylePlayersRetriever {
    fun getPlayersListForGameStyle(gameStyle: GameStyle): List<Player>
}

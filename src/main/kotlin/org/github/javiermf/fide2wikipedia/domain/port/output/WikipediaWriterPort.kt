package org.github.javiermf.fide2wikipedia.domain.port.output

import org.github.javiermf.fide2wikipedia.domain.model.GameStyle
import org.github.javiermf.fide2wikipedia.domain.model.Player

interface WikipediaWriterPort {
    fun writeGameStyleSection(gameStyle: GameStyle, players: List<Player>)
}
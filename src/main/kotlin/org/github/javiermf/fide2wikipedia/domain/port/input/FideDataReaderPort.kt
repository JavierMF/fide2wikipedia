package org.github.javiermf.fide2wikipedia.domain.port.input

import org.github.javiermf.fide2wikipedia.domain.model.Player

interface FideDataReaderPort {
    fun readFideData(filePath: String): List<Player>
}

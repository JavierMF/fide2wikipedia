package org.github.javiermf.fide2wikipedia.domain.service

import org.github.javiermf.fide2wikipedia.domain.model.Player

interface FideDataReader {
    fun readFideData(filePath: String): List<Player>
}

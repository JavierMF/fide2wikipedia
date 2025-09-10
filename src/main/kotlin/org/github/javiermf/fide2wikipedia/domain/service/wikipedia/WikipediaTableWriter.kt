package org.github.javiermf.fide2wikipedia.domain.service.wikipedia

import org.github.javiermf.fide2wikipedia.domain.model.GameStyle
import org.github.javiermf.fide2wikipedia.domain.model.Player
import org.github.javiermf.fide2wikipedia.domain.model.SectionContents
import org.github.javiermf.fide2wikipedia.domain.model.SectionDefinition
import org.github.javiermf.fide2wikipedia.domain.service.NameMapper
import org.github.javiermf.fide2wikipedia.domain.service.OutputFileWriter

class WikipediaTableWriter(
    private val nameMapper: NameMapper,
    private val output: OutputFileWriter
) {

    fun writeGameStyleSection(gameStyle: GameStyle, players: List<Player>) {
        println("Writing players for ${gameStyle.title}...")

        output.writeContent("=== <u>${gameStyle.title}</u> ===\n")

        val wikiTableWriter = WikiTableWriter(output, nameMapper)
        println("Writing open rating")
        wikiTableWriter.writeSection(SectionContents(players, SectionDefinition.OPEN_TOP, gameStyle))
        println("Writing women rating")
        wikiTableWriter.writeSection(SectionContents(players, SectionDefinition.FEM_TOP, gameStyle))
        println("Writing junior open rating")
        wikiTableWriter.writeSection(SectionContents(players, SectionDefinition.OPEN_JUV, gameStyle))
        println("Writing women open rating")
        wikiTableWriter.writeSection(SectionContents(players, SectionDefinition.FEM_JUV, gameStyle))
        println("Finished")
    }
}

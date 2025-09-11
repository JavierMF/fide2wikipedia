package org.github.javiermf.fide2wikipedia.domain.service.wikipedia

import org.github.javiermf.fide2wikipedia.domain.model.GameStyle
import org.github.javiermf.fide2wikipedia.domain.model.Player
import org.github.javiermf.fide2wikipedia.domain.model.SectionContents
import org.github.javiermf.fide2wikipedia.domain.model.SectionDefinition
import org.github.javiermf.fide2wikipedia.domain.service.NameMapper
import org.github.javiermf.fide2wikipedia.domain.service.OutputFileWriter
import java.time.Clock

class WikipediaTableWriter(
    private val output: OutputFileWriter,
    private val clock: Clock
) {

    fun writeGameStyleSection(gameStyle: GameStyle, players: List<Player>) {
        println("Writing players for ${gameStyle.title}...")

        output.writeContent("=== <u>${gameStyle.title}</u> ===\n")

        val gameSectionTableWriter = GameSectionTableWriter(output, NameMapper(), clock)
        println("Writing open rating")
        gameSectionTableWriter.writeSection(SectionContents(players, SectionDefinition.OPEN_TOP, gameStyle, clock))
        println("Writing women rating")
        gameSectionTableWriter.writeSection(SectionContents(players, SectionDefinition.FEM_TOP, gameStyle, clock))
        println("Writing junior open rating")
        gameSectionTableWriter.writeSection(SectionContents(players, SectionDefinition.OPEN_JUV, gameStyle, clock))
        println("Writing women open rating")
        gameSectionTableWriter.writeSection(SectionContents(players, SectionDefinition.FEM_JUV, gameStyle, clock))
        println("Finished")
    }
}

package org.github.javiermf.fide2wikipedia.adapter.output.wikipedia

import org.github.javiermf.fide2wikipedia.domain.model.GameStyle
import org.github.javiermf.fide2wikipedia.domain.port.input.FideDataReaderPort
import org.github.javiermf.fide2wikipedia.domain.port.output.WikipediaWriterPort
import org.github.javiermf.fide2wikipedia.domain.model.Player
import org.github.javiermf.fide2wikipedia.domain.service.NameMapper
import org.github.javiermf.fide2wikipedia.util.appenderStream
import java.io.File

class WikiGameStyleWriter(
    private val nameMapper: NameMapper,
    private val outputFile: File
) : WikipediaWriterPort {

    override fun writeGameStyleSection(gameStyle: GameStyle, players: List<Player>) {
        println("Writing players for ${gameStyle.title}...")

        outputFile.appenderStream()
            .use { out -> out.write("=== <u>${gameStyle.title}</u> ==="); out.newLine() }

        val wikiTableWriter = WikiTableWriter(outputFile, players, nameMapper, gameStyle)
        println("Writing open rating")
        wikiTableWriter.writeSection(SectionDefinition.OPEN_TOP)
        println("Writing women rating")
        wikiTableWriter.writeSection(SectionDefinition.FEM_TOP)
        println("Writing junior open rating")
        wikiTableWriter.writeSection(SectionDefinition.OPEN_JUV)
        println("Writing women open rating")
        wikiTableWriter.writeSection(SectionDefinition.FEM_JUV)
        println("Finished")
    }
}

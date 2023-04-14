package org.github.javiermf.fide2wikipedia

import java.io.File

class WikiGameStyleWriter(
    private val gameStyle: GameStyle,
    private val ratingsFile: File,
    private val outputFile: File
) {

    fun writeToFile() {
        println("Reading players for ${gameStyle.title}...")
        val players = FideDataReader(ratingsFile).readPlayersRatingsFromLocalFile()

        outputFile.appenderStream()
            .use { out -> out.write("=== <u>${gameStyle.title}</u> ==="); out.newLine() }

        val wikiTableWriter = WikiTableWriter(outputFile, players, NameMapper(), gameStyle)
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

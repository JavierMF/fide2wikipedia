package org.github.javiermf.fide2wikipedia.input.cli

import org.github.javiermf.fide2wikipedia.domain.GenerateWikipediaTableFromFideRatingsUseCase
import org.github.javiermf.fide2wikipedia.infrastructure.filereader.FideDataXMLReader
import org.github.javiermf.fide2wikipedia.infrastructure.filereader.FilesLister
import org.github.javiermf.fide2wikipedia.infrastructure.filereader.GameStylePlayersRetrieverLocal
import org.github.javiermf.fide2wikipedia.infrastructure.filewriter.OutputLocalFileWriter
import java.io.File
import java.time.Clock
import kotlin.system.exitProcess

fun main(args: Array<String>) {

    if (args.isEmpty()) {
        println("Expecting as argument a folder with the three FIDE XML data files")
        exitProcess(-1)
    }

    val folderPath = args.first()
    val clock = Clock.systemDefaultZone()

    GenerateWikipediaTableFromFideRatingsUseCase(
        playersRetriever = GameStylePlayersRetrieverLocal(
            filesLister = FilesLister(folderPath),
            fideDataXMLReader = FideDataXMLReader()
        ),
        outputWriter = OutputLocalFileWriter(File("output.txt")),
        clock = clock
    ).generate()

}


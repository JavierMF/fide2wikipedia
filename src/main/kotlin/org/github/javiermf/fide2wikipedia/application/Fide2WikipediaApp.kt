package org.github.javiermf.fide2wikipedia.application

import org.github.javiermf.fide2wikipedia.adapter.input.cli.FilesLister
import org.github.javiermf.fide2wikipedia.adapter.output.wikipedia.WikiGameStyleWriter
import org.github.javiermf.fide2wikipedia.adapter.output.xml.FideDataReader
import org.github.javiermf.fide2wikipedia.domain.model.GameStyle
import org.github.javiermf.fide2wikipedia.domain.service.NameMapper
import java.io.File
import kotlin.system.exitProcess

fun main(args: Array<String>) {

    if (args.isEmpty()) {
        println("Expecting as argument a folder with the three FIDE XML data files")
        exitProcess(-1)
    }

    val folderPath = args.first()
    val fileLister = FilesLister(folderPath)

    val outputFileName = "output.txt"
    val outputFile = File(outputFileName)
    if (outputFile.exists()) outputFile.delete()

    // Manual Dependency Injection
    val fideDataReader = FideDataReader()
    val nameMapper = NameMapper()
    val wikiGameStyleWriter = WikiGameStyleWriter(nameMapper, outputFile)

    listOf(GameStyle.OPEN, GameStyle.RAPID, GameStyle.BULLET)
        .forEach {gameStyle ->
            val ratingsFile = fileLister.getRatingFileForStyle(gameStyle)
            val players = fideDataReader.readFideData(ratingsFile.absolutePath)
            wikiGameStyleWriter.writeGameStyleSection(gameStyle, players)
        }

}


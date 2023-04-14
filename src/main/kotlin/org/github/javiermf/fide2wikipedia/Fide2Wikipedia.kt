package org.github.javiermf.fide2wikipedia

import java.io.File
import java.io.FileOutputStream
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

    listOf(GameStyle.OPEN, GameStyle.RAPID, GameStyle.BULLET)
        .forEach {gameStyle ->
            val ratingsFile = fileLister.getRatingFileForStyle(gameStyle)
            WikiGameStyleWriter(gameStyle, ratingsFile, outputFile).writeToFile()
        }

}

fun File.appenderStream() = FileOutputStream(this, true).bufferedWriter()


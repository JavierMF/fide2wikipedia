package org.github.javiermf.fide2wikipedia

import kotlin.system.exitProcess

fun main(args: Array<String>) {

    if (args.isEmpty()) {
        println("Expecting as argument the FIDE XML data file")
        exitProcess(-1)
    }

    println("Reading players...")
    val players = FideDataReader(args.first()).readPlayersRatingsFromLocalFile()

    val wikiTableWriter = WikiTableWriter(players, NameMapper())
    println("Writing open rating")
    wikiTableWriter.writeTopOpen()
    println("Writing women rating")
    wikiTableWriter.writeTopWomen()
    println("Writing junior open rating")
    wikiTableWriter.writeJuniorOpen()
    println("Writing women open rating")
    wikiTableWriter.writeJuniorWomen()
    println("Finished")
}

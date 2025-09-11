package org.github.javiermf.fide2wikipedia.input.cli

import org.github.javiermf.fide2wikipedia.domain.GenerateWikipediaTableFromFideRatingsUseCase
import org.github.javiermf.fide2wikipedia.infrastructure.downloader.FideFilesDownloader
import org.github.javiermf.fide2wikipedia.infrastructure.filewriter.OutputLocalFileWriter
import java.io.File
import java.time.Clock
import java.time.format.DateTimeFormatter

fun main() {

    val downloader = FideFilesDownloader()
    val clock = Clock.systemDefaultZone()

    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val currentDate = dateFormatter.format(clock.instant().atZone(clock.zone))
    val outputFileName = "output-$currentDate.txt"

    GenerateWikipediaTableFromFideRatingsUseCase(
        downloader = downloader,
        outputWriter = OutputLocalFileWriter(File(outputFileName)),
        clock = clock
    ).generate()

}


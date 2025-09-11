package org.github.javiermf.fide2wikipedia.input.cli

import org.github.javiermf.fide2wikipedia.domain.GenerateWikipediaTableFromFideRatingsUseCase
import org.github.javiermf.fide2wikipedia.infrastructure.downloader.FideFilesDownloader
import org.github.javiermf.fide2wikipedia.infrastructure.filewriter.OutputLocalFileWriter
import java.io.File
import java.time.Clock

fun main() {

    val downloader = FideFilesDownloader()
    val clock = Clock.systemDefaultZone()

    GenerateWikipediaTableFromFideRatingsUseCase(
        downloader = downloader,
        outputWriter = OutputLocalFileWriter(File("output.txt")),
        clock = clock
    ).generate()

}


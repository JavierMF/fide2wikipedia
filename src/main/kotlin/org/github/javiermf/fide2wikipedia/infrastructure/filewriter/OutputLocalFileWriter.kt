package org.github.javiermf.fide2wikipedia.infrastructure.filewriter

import org.github.javiermf.fide2wikipedia.domain.service.OutputFileWriter
import java.io.File
import java.io.FileOutputStream
import java.io.Writer

class OutputLocalFileWriter(outputFile: File): OutputFileWriter {

    private val writer: Writer

    init {
        if (outputFile.exists()) outputFile.delete()
        writer = FileOutputStream(outputFile, true).writer()
    }

    override fun writeContent(content: String) {
        writer.write(content)
        writer.flush()
    }
}

package org.github.javiermf.fide2wikipedia.adapter.input.cli

import io.kotest.matchers.shouldBe
import org.github.javiermf.fide2wikipedia.domain.model.GameStyle
import org.github.javiermf.fide2wikipedia.infrastructure.filereader.FilesLister
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File

class FilesListerTest {

    @TempDir
    lateinit var tempDir: File

    @Test
    fun `getRatingFileForStyle returns correct file for game style`() {
        // Create dummy files
        File(tempDir, "standard_ratings.xml").createNewFile()
        File(tempDir, "rapid_ratings.xml").createNewFile()
        File(tempDir, "blitz_ratings.xml").createNewFile()

        val filesLister = FilesLister(tempDir.absolutePath)

        filesLister.getRatingFileForStyle(GameStyle.OPEN).name shouldBe "standard_ratings.xml"
        filesLister.getRatingFileForStyle(GameStyle.RAPID).name shouldBe "rapid_ratings.xml"
        filesLister.getRatingFileForStyle(GameStyle.BULLET).name shouldBe "blitz_ratings.xml"
    }

    @Test
    fun `FilesLister throws exception if path not found`() {
        val nonExistentPath = "/non/existent/path"
        val exception = org.junit.jupiter.api.assertThrows<RuntimeException> {
            FilesLister(nonExistentPath)
        }
        exception.message shouldBe "Path not found $nonExistentPath"
    }

    @Test
    fun `FilesLister throws exception if path is not a directory`() {
        val tempFile = File(tempDir, "not_a_directory.txt")
        tempFile.createNewFile()

        val exception = org.junit.jupiter.api.assertThrows<RuntimeException> {
            FilesLister(tempFile.absolutePath)
        }
        exception.message shouldBe "${tempFile.absolutePath} must be a directory"
    }

    @Test
    fun `FilesLister throws exception if some rating file is missing`() {
        // Create only two files
        File(tempDir, "standard_ratings.xml").createNewFile()
        File(tempDir, "rapid_ratings.xml").createNewFile()

        val exception = org.junit.jupiter.api.assertThrows<RuntimeException> {
            FilesLister(tempDir.absolutePath)
        }
        exception.message shouldBe "Some rating file are missing. Only 2 found."
    }
}

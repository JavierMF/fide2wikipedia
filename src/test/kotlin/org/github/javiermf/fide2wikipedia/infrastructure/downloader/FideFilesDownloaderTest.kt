package org.github.javiermf.fide2wikipedia.infrastructure.downloader

import io.kotest.matchers.shouldBe
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Buffer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

class FideFilesDownloaderTest {

    private lateinit var server: MockWebServer

    @BeforeEach
    fun setUp() {
        server = MockWebServer()
        server.start()
    }

    @AfterEach
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `downloadAndUnzipFideFiles downloads and unzips files`() {
        // Create dummy zip files
        val standardZip = createDummyZip("standard_rating_list.xml")
        val rapidZip = createDummyZip("rapid_rating_list.xml")
        val blitzZip = createDummyZip("blitz_rating_list.xml")

        // Enqueue responses
        server.enqueue(MockResponse().setBody(standardZip))
        server.enqueue(MockResponse().setBody(rapidZip))
        server.enqueue(MockResponse().setBody(blitzZip))

        val downloader = FideFilesDownloader(baseUrl = server.url("").toString().removeSuffix("/"))
        val tempDirPath = downloader.downloadAndUnzipFideFiles()

        val tempDir = File(tempDirPath)
        val files = tempDir.listFiles()?.map { it.name }?.toSet()
        files shouldBe setOf("standard_rating_list.xml", "rapid_rating_list.xml", "blitz_rating_list.xml")

        tempDir.deleteRecursively()
    }

    private fun createDummyZip(entryName: String): Buffer {
        val buffer = Buffer()
        ZipOutputStream(buffer.outputStream()).use { zos ->
            zos.putNextEntry(ZipEntry(entryName))
            zos.write("<playerslist></playerslist>".toByteArray())
            zos.closeEntry()
        }
        return buffer
    }
}

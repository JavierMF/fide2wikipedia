package org.github.javiermf.fide2wikipedia.infrastructure.downloader

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URI
import java.nio.file.Files
import java.util.zip.ZipInputStream

class FideFilesDownloader(private val baseUrl: String = "http://ratings.fide.com/download") {

    fun downloadAndUnzipFideFiles(): String {
        val tempDir = Files.createTempDirectory("fide-ratings").toFile()
        println("Created temporary directory: ${tempDir.absolutePath}")

        val urls = listOf(
            "$baseUrl/standard_rating_list_xml.zip",
            "$baseUrl/rapid_rating_list_xml.zip",
            "$baseUrl/blitz_rating_list_xml.zip"
        )

        urls.forEach { urlString ->
            val url = URI(urlString).toURL()
            val fileName = urlString.substringAfterLast("/")
            val zipFile = File(tempDir, fileName)

            println("Downloading $urlString to ${zipFile.absolutePath}")
            url.openStream().use { input ->
                FileOutputStream(zipFile).use { output ->
                    input.copyTo(output)
                }
            }
            println("Downloaded ${zipFile.absolutePath}")

            println("Unzipping ${zipFile.absolutePath}")
            ZipInputStream(zipFile.inputStream()).use { zis ->
                var zipEntry = zis.nextEntry
                while (zipEntry != null) {
                    val newFile = File(tempDir, zipEntry.name)
                    // to avoid Zip Slip vulnerability
                    if (!newFile.canonicalPath.startsWith(tempDir.canonicalPath + File.separator)) {
                        throw SecurityException("Zip entry is outside of the target dir: " + zipEntry.name)
                    }
                    if (zipEntry.isDirectory) {
                        if (!newFile.isDirectory && !newFile.mkdirs()) {
                            throw IOException("Failed to create directory " + newFile)
                        }
                    } else {
                        // fix for Windows-created archives
                        val parent = newFile.parentFile
                        if (parent != null) {
                            if (!parent.isDirectory && !parent.mkdirs()) {
                                throw IOException("Failed to create directory " + parent)
                            }
                        }
                        FileOutputStream(newFile).use { fos ->
                            zis.copyTo(fos)
                        }
                    }
                    zipEntry = zis.nextEntry
                }
                zis.closeEntry()
            }
            println("Unzipped ${zipFile.absolutePath}")
            zipFile.delete()
        }

        return tempDir.absolutePath
    }
}

package org.github.javiermf.fide2wikipedia.infrastructure.backend.xml

import io.kotest.matchers.shouldBe
import org.github.javiermf.fide2wikipedia.infrastructure.filereader.FideDataXMLReader
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File

class FideDataReaderTest {

    @TempDir
    lateinit var tempDir: File

    @Test
    fun `readFideData reads players from XML file`() {
        val xmlContent = """
            <playerslist>
                <player>
                    <name>Carlsen, Magnus</name>
                    <country>NOR</country>
                    <sex>M</sex>
                    <rating>2850</rating>
                    <birthday>1990</birthday>
                </player>
                <player>
                    <name>Caruana, Fabiano</name>
                    <country>USA</country>
                    <sex>M</sex>
                    <rating>2800</rating>
                    <birthday>1992</birthday>
                </player>
            </playerslist>
        """.trimIndent()

        val tempFile = File(tempDir, "ratings.xml")
        tempFile.writeText(xmlContent)

        val reader = FideDataXMLReader()
        val players = reader.readFideData(tempFile.absolutePath)

        players.size shouldBe 2
        players[0].name shouldBe "Carlsen, Magnus"
        players[0].country shouldBe "NOR"
        players[0].rating shouldBe 2850
        players[1].name shouldBe "Caruana, Fabiano"
        players[1].country shouldBe "USA"
        players[1].rating shouldBe 2800
    }

    @Test
    fun `readFideData returns empty list for empty XML`() {
        val xmlContent = """
            <playerslist>
            </playerslist>
        """.trimIndent()

        val tempFile = File(tempDir, "empty_ratings.xml")
        tempFile.writeText(xmlContent)

        val reader = FideDataXMLReader()
        val players = reader.readFideData(tempFile.absolutePath)

        players.size shouldBe 0
    }

    @Test
    fun `readFideData returns empty list for non-existent file`() {
        val reader = FideDataXMLReader()
        val players = reader.readFideData("non_existent.xml")

        players.size shouldBe 0
    }
}

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
        players[0].name shouldBe "Magnus Carlsen"
        players[0].country shouldBe "NOR"
        players[0].rating shouldBe 2850
        players[1].name shouldBe "Fabiano Caruana"
        players[1].country shouldBe "USA"
        players[1].rating shouldBe 2800
    }

    @Test
    fun `readFideData reads female player from XML file`() {
        val xmlContent = """
            <playerslist>
                <player>
                    <fideid>88180085</fideid>
                    <name>Aadhira V</name>
                    <country>IND</country>
                    <sex>F</sex>
                    <title></title>
                    <w_title></w_title>
                    <o_title></o_title>
                    <foa_title></foa_title>
                    <rating>1415</rating>
                    <games>0</games>
                    <k>40</k>
                    <birthday>2011</birthday>
                    <flag>w</flag>
                </player>
                <player>
                    <fideid>25101242</fideid>
                    <name>Aadhiseshan K</name>
                    <country>IND</country>
                    <sex>M</sex>
                    <title></title>
                    <w_title></w_title>
                    <o_title></o_title>
                    <foa_title></foa_title>
                    <rating>1499</rating>
                    <games>0</games>
                    <k>20</k>
                    <birthday>2005</birthday>
                    <flag>i</flag>
                </player>
            </playerslist>
        """.trimIndent()

        val tempFile = File(tempDir, "ratings.xml")
        tempFile.writeText(xmlContent)

        val reader = FideDataXMLReader()
        val players = reader.readFideData(tempFile.absolutePath)

        players.size shouldBe 2
        players[0].name shouldBe "Aadhira V"
        players[0].country shouldBe "IND"
        players[0].sex shouldBe "F"
        players[0].rating shouldBe 1415
        players[0].birthday shouldBe 2011
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

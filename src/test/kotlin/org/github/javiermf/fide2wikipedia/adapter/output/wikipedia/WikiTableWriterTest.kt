package org.github.javiermf.fide2wikipedia.infrastructure.backend.wikipedia

import io.kotest.matchers.shouldBe
import org.github.javiermf.fide2wikipedia.domain.model.GameStyle
import org.github.javiermf.fide2wikipedia.domain.model.Player
import org.github.javiermf.fide2wikipedia.domain.model.SectionContents
import org.github.javiermf.fide2wikipedia.domain.model.SectionDefinition
import org.github.javiermf.fide2wikipedia.domain.service.NameMapper
import org.github.javiermf.fide2wikipedia.infrastructure.filewriter.OutputLocalFileWriter
import org.github.javiermf.fide2wikipedia.domain.service.wikipedia.WikiTableWriter
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File

class WikiTableWriterTest {

    @TempDir
    lateinit var tempDir: File

    private val nameMapper = NameMapper()
    private val gameStyle = GameStyle.OPEN

    private fun createDummyPlayers(count: Int, sex: String = "M"): List<Player> {
        return (1..count).map { i ->
            Player("Player $i", "FED", sex, 2000 + i, 1990 + i)
        }
    }

    @Test
    fun `writeSection writes correct header and table for OPEN_TOP`() {
        val outputFile = File(tempDir, "output.txt")
        val players = createDummyPlayers(3)
        val writer = WikiTableWriter(OutputLocalFileWriter(outputFile),  nameMapper)

        writer.writeSection(SectionContents(players, SectionDefinition.OPEN_TOP, gameStyle))

        val content = outputFile.readText()
        content shouldBe """
    ==== Abierto ====
    Los 20 primeros jugadores de ajedrez del mundo en septiembre de 2025.<ref name=":0">{{Cita web|url=https://ratings.fide.com/top_lists.phtml|título=FIDE Ratings|fechaacceso=10-09-2025|sitioweb=ratings.fide.com}}</ref>

    {| class="wikitable"
    |-
    ! Posición
    ! Jugador
    ! Federación
    ! ELO
    |-
    ! 1
    | [[Player 3]]
    | {{FED}}
    | style="text-align:center" |2003
    |-
    ! 2
    | [[Player 2]]
    | {{FED}}
    | style="text-align:center" |2002
    |-
    ! 3
    | [[Player 1]]
    | {{FED}}
    | style="text-align:center" |2001
    |}
    
    """.trimIndent()
    }
}

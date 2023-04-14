package org.github.javiermf.fide2wikipedia

import java.io.BufferedWriter
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class WikiTableWriter(
    private val outputFile: File,
    private val players: List<Player>,
    private val nameMapper: NameMapper,
    private val gameStyle: GameStyle
) {

    private fun writeSectionHeader(section: SectionDefinition) {
        val sectionDesc = sectionDescGenerator(section, gameStyle)
        val dateFormat = DateTimeFormatter.ofPattern("dd-mm-yyyy", Locale.forLanguageTag("ES"))
        val todayDesc = dateFormat.format(LocalDateTime.now())
        val sectionHeader = """
    ==== ${section.title} ====
    $sectionDesc<ref name=":0">{{Cita web|url=https://ratings.fide.com/top_lists.phtml|título=FIDE Ratings|fechaacceso=$todayDesc|sitioweb=ratings.fide.com}}</ref>
    
""".trimIndent()
        outputFile.appenderStream()
            .use { out -> out.appendLine(sectionHeader) }
    }

    fun writeSection(section: SectionDefinition) {
        writeSectionHeader(section)
        writeWikiTable(section.filterFunc)
    }

    private fun writeWikiTable(filterFunc: (p: Player) -> Boolean) =
        outputFile.appenderStream()
            .use { out ->
                writeWikiTableHeader(out)
                players
                    .filter(filterFunc)
                    .sortedByDescending { it.rating }
                    .subList(0, NUMBER_OF_TOP_PLAYERS)
                    .forEachIndexed { index, player -> writePlayerRow(out, index, player) }
                writeWikiTableFooter(out)
            }

    private fun writePlayerRow(out: BufferedWriter, index: Int, player: Player) {
        val fullName = nameMapper.mapName(player.fullName())
        out.appendLine("""
            |-
            ! ${index + 1}
            | [[$fullName]]
            | {{${player.country}}}
            | style="text-align:center" |${player.rating}
        """.trimIndent())
    }

    private fun writeWikiTableFooter(out: BufferedWriter) = out.appendLine("|}\n")

    private fun writeWikiTableHeader(out: BufferedWriter) =
        out.appendLine("""
    {| class="wikitable"
    |-
    ! Posición
    ! Jugador
    ! Federación
    ! ELO
""".trimIndent())

    companion object {
        const val NUMBER_OF_TOP_PLAYERS = 20
        private val dateFormat = DateTimeFormatter.ofPattern("MMMM 'de' yyyy", Locale.forLanguageTag("ES"))

        fun sectionDescGenerator(sectionDefinition: SectionDefinition, gameStyle: GameStyle): String {
            val genre = if (sectionDefinition.genre == Genre.OPEN)
                "Los $NUMBER_OF_TOP_PLAYERS primeros jugadores"
            else
                "Las $NUMBER_OF_TOP_PLAYERS primeras jugadoras"

            val sectionDesc = if (sectionDefinition.age == Age.JUVENILE) "$genre juveniles" else genre

            val dateDesc = dateFormat.format(LocalDateTime.now())
            return "$sectionDesc de ${gameStyle.writingName} del mundo en $dateDesc."
        }
    }
}

enum class Genre { OPEN, FEMALE }

enum class Age { OPEN, JUVENILE }

enum class SectionDefinition(
    val genre: Genre,
    val age: Age,
    val title: String,
    val filterFunc: (p: Player) -> Boolean)
{
    OPEN_TOP(Genre.OPEN, Age.OPEN, "Abierto", { player -> player.isActive() }),
    FEM_TOP(Genre.FEMALE, Age.OPEN, "Femenino", { player -> player.isFemale() }),
    OPEN_JUV(Genre.OPEN, Age.JUVENILE, "Juvenil", { player -> player.isActive() && player.isJunior() }),
    FEM_JUV(Genre.FEMALE, Age.JUVENILE, "Juvenil femenino", { player -> player.isFemale() && player.isJunior() }),
}


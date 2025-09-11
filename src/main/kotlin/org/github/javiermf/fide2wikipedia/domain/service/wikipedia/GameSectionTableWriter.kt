package org.github.javiermf.fide2wikipedia.domain.service.wikipedia

import java.time.Clock
import org.github.javiermf.fide2wikipedia.domain.model.Player
import org.github.javiermf.fide2wikipedia.domain.model.SectionContents
import org.github.javiermf.fide2wikipedia.domain.service.NameMapper
import org.github.javiermf.fide2wikipedia.domain.service.OutputFileWriter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class GameSectionTableWriter(
    private val outputWriter: OutputFileWriter,
    private val nameMapper: NameMapper,
    private val clock: Clock
) {

    fun writeSection(sectionContents: SectionContents) {
        writeSectionHeader(sectionContents)
        writeWikiTable(sectionContents)
    }

    private fun writeSectionHeader(sectionData: SectionContents) {
        val todayDesc: String = dateFormat.format(LocalDateTime.now(clock))
        val sectionHeader = """
    ==== ${sectionData.title} ====
    ${sectionData.sectionDescription}<ref name=":0">{{Cita web|url=https://ratings.fide.com/top_lists.phtml|título=FIDE Ratings|fechaacceso=$todayDesc|sitioweb=ratings.fide.com}}</ref>

    
""".trimIndent()
        outputWriter.writeContent(sectionHeader)
    }

    private fun writeWikiTable(sectionData: SectionContents) {
        outputWriter.writeContent(WIKI_TABLE_HEADER)
        sectionData
            .players
            .forEachIndexed { index, player -> outputWriter.writeContent(playerRowContent(index, player)) }
        outputWriter.writeContent(WIKI_TABLE_FOOTER)
    }

    private fun playerRowContent(index: Int, player: Player): String {
        val fullName = nameMapper.mapName(player.fullName())
        return """
            |-
            ! ${index + 1}
            | [[$fullName]]
            | {{${player.country}}}
            | style="text-align:center" |${player.rating}
            
        """.trimIndent()
    }

    companion object {
        val WIKI_TABLE_HEADER =  """
    {| class="wikitable"
    |-
    ! Posición
    ! Jugador
    ! Federación
    ! ELO
    
""".trimIndent()
        const val WIKI_TABLE_FOOTER = "|}\n\n"
        val dateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.forLanguageTag("ES"))
    }
}

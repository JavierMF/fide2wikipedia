package org.github.javiermf.fide2wikipedia

import java.io.File
import java.io.PrintWriter

class WikiTableWriter(
    private val players: List<Player>,
    private val nameMapper: NameMapper
) {
    private val numberOfTopPlayers = 100

    fun writeTopOpen() =
        writeWikiTable("top-absolute-open.txt") { player -> player.isActive() }

    fun writeTopWomen() =
        writeWikiTable("top-absolute-women.txt") { player -> player.isFemale() }

    fun writeJuniorOpen() =
        writeWikiTable("top-junior-open.txt") { player -> player.isActive() && player.isJunior() }

    fun writeJuniorWomen() =
        writeWikiTable("top-junior-women.txt") { player -> player.isFemale() && player.isJunior() }

    private fun writeWikiTable(fileName: String, filterFunc: (p: Player) -> Boolean) =
        File(fileName)
            .printWriter()
            .use { out ->
                writeWikiTableHeader(out)
                players
                    .filter(filterFunc)
                    .sortedByDescending { it.rating }
                    .subList(0, numberOfTopPlayers)
                    .forEachIndexed { index, player -> writePlayerRow(out, index, player) }
                writeWikiTableFooter(out)
            }

    private fun writePlayerRow(out: PrintWriter, index: Int, player: Player) {
        val fullName = nameMapper.mapName(player.fullName())
        out.println("""
            |-
            ! ${index + 1}
            | [[$fullName]]
            | {{${player.country}}}
            | style="text-align:center" |${player.rating}
        """.trimIndent())
    }

    private fun writeWikiTableFooter(out: PrintWriter) =
        out.println("|}")

    private fun writeWikiTableHeader(out: PrintWriter) =
        out.println("""
    {| class="wikitable"
    |-
    ! Posición
    ! Jugador
    ! Federación
    ! ELO
""".trimIndent())

}

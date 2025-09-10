package org.github.javiermf.fide2wikipedia.domain.model

import java.time.Clock
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

data class SectionContents(
    val title: String,
    val players: List<Player>,
    val sectionDescription: String
) {
    constructor(allPlayers: List<Player>, section: SectionDefinition, gameStyle: GameStyle, clock: Clock) : this(
        section.title,
        allPlayers
            .filter { player -> section.filterFunc(player, clock) }
            .sortedByDescending { it.rating }
            .take(NUMBER_OF_TOP_PLAYERS),
        sectionDescGenerator(section, gameStyle, clock)
    )

    companion object {
        const val NUMBER_OF_TOP_PLAYERS = 20

        private val dateFormat = DateTimeFormatter.ofPattern("MMMM 'de' yyyy", Locale.forLanguageTag("ES"))
        private fun sectionDescGenerator(sectionDefinition: SectionDefinition, gameStyle: GameStyle, clock: Clock): String {
            val genre = if (sectionDefinition.genre == Genre.OPEN)
                "Los $NUMBER_OF_TOP_PLAYERS primeros jugadores"
            else
                "Las $NUMBER_OF_TOP_PLAYERS primeras jugadoras"

            val sectionDesc = if (sectionDefinition.age == Age.JUVENILE) "$genre juveniles" else genre

            val dateDesc = dateFormat.format(LocalDateTime.now(clock))
            return "$sectionDesc de ${gameStyle.writingName} del mundo en $dateDesc."
        }
    }

}

package org.github.javiermf.fide2wikipedia

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import org.simpleframework.xml.core.Persister
import java.io.File

class FideDataReader(
    private val ratingsFile: File
) {

    fun readPlayersRatingsFromLocalFile(): List<Player> {
        val dataFetch = Persister().read(Players::class.java, ratingsFile)
        return dataFetch.players ?: emptyList()
    }
}

@Root(strict = false, name = "player")
data class Player(
    @field:Element(name = "name")
    var name: String? = null,

    @field:Element(name = "country")
    var countryOrig: String? = null,

    @field:Element(name = "sex")
    var sex: String? = null,

    @field:Element(name = "flag", required = false)
    var flag: String? = null,

    @field:Element(name = "rating")
    var rating: Int? = null,

    @field:Element(name = "birthday", required = false)
    var birthday: Int = 0,
) {
    private val nameSegments by lazy {name!!.split(", ")}
    private val firstname: String by lazy { if (nameSegments.size >= 2) nameSegments[1].trim() else "" }
    private val surname: String by lazy { if (nameSegments.isNotEmpty()) nameSegments[0].trim() else "" }

    val country: String? get() = if (countryOrig?.contains("FID") == true) "RUS" else countryOrig

    fun isFemale() = flag == "w"
    fun isActive() = flag in setOf(null, "w")
    fun isJunior() = birthday > juniorLimitYear
    fun fullName () = "$firstname $surname".trim()

    override fun toString() = "$firstname $surname, $country, $rating"

    companion object {
        private val currentYear = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).year
        val juniorLimitYear = currentYear - 21
    }
}

@Root(strict = false, name = "playerslist")
data class Players(
    @field:ElementList(name = "player", inline = true)
    var players: List<Player>? = null
)

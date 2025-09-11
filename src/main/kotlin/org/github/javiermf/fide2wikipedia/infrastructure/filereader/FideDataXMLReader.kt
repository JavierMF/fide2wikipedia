package org.github.javiermf.fide2wikipedia.infrastructure.filereader

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import org.simpleframework.xml.core.Persister
import java.io.File
import org.github.javiermf.fide2wikipedia.domain.model.Player as DomainPlayer // Alias for domain Player

class FideDataXMLReader {

    fun readFideData(filePath: String): List<DomainPlayer> =
        try {
            val dataFetch = Persister().read(Players::class.java, File(filePath))
            dataFetch.players?.map {
                DomainPlayer(
                    name = it.fullName,
                    country = it.country ?: "",
                    sex = it.sex ?: "",
                    rating = it.rating ?: 0,
                    birthday = it.birthday,
                    isActive = it.isActive
                )
            } ?: emptyList()
        } catch (e: Exception) {
            // Log the exception if necessary
            emptyList()
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

    val fullName: String get() = "$firstname $surname".trim()
    val country: String? get() = if (countryOrig?.contains("FID") == true) "RUS" else countryOrig

    val isActive: Boolean get() = flag != "i"

    override fun toString() = "$firstname $surname, $country, $rating"
}

@Root(strict = false, name = "playerslist")
data class Players(
    @field:ElementList(name = "player", inline = true)
    var players: List<Player>? = null
)

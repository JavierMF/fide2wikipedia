package org.github.javiermf.fide2wikipedia

class NameMapper {

    private val nameMappings= mapOf(
        "Liren Ding" to "Ding Liren",
        "Hao Wang" to "Wang Hao (ajedrecista)|Hao Wang",
        "Yi Wei" to "Wei Yi|Yi Wei",
        "Santosh Gujrathi Vidit" to "Vidit Gujrathi|Santosh Gujrathi Vidit",
        "Dmitry Andreikin" to "Dmitri Andreikin|Dmitry Andreikin",
        "Quang Liem Le" to "Lê Quang Liêm|Quang Liem Le",
        "David Anton Guijarro" to "David Antón|David Anton Guijarro"
    )

    fun mapName(name:String) = nameMappings[name] ?: name
}
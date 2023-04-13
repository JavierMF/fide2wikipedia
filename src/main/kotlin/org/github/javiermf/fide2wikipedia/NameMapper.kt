package org.github.javiermf.fide2wikipedia

class NameMapper {

    private val nameMappings= mapOf(
        "Liren Ding" to "Ding Liren",
        "Hao Wang" to "Wang Hao (ajedrecista)|Hao Wang",
        "Yi Wei" to "Wei Yi|Yi Wei",
        "Santosh Gujrathi Vidit" to "Vidit Gujrathi|Santosh Gujrathi Vidit",
        "Dmitry Andreikin" to "Dmitri Andreikin|Dmitry Andreikin",
        "Quang Liem Le" to "Lê Quang Liêm|Quang Liem Le",
        "David Anton Guijarro" to "David Antón|David Anton Guijarro",
        "Praggnanandhaa R" to "Rameshbabu Praggnanandhaa",
        "Alice Lee" to "Alice Lee (ajedrecista)|Alice Lee",
        "Ann Matnadze Bujiashvili" to "Ana Matnadze|Ann Matnadze Bujiashvili",
        "Zhongyi Tan" to "Tan Zhongyi",
        "Wenjun Ju" to "Ju Wenjun",
        "Hans Moke Niemann" to "Hans Niemann",
        "Tingjie Lei" to "Lei Tingjie"
    )

    fun mapName(name:String) = nameMappings[name] ?: name
}

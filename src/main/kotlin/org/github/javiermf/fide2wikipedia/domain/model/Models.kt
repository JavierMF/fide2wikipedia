package org.github.javiermf.fide2wikipedia.domain.model

enum class GameStyle(
    val title: String,
    val writingName: String
){
    OPEN("Estándar", "ajedrez"),
    RAPID("Rápido", "ajedrez rápido"),
    BULLET("Relámpago", "ajedrez relámpago");
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

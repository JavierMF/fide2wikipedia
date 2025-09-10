package org.github.javiermf.fide2wikipedia.domain.model

import java.time.Clock

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
    val filterFunc: (p: Player, clock: Clock) -> Boolean)
{
    OPEN_TOP(Genre.OPEN, Age.OPEN, "Abierto", { player, _ -> player.isActive }),
    FEM_TOP(Genre.FEMALE, Age.OPEN, "Femenino", { player, _ -> player.isFemale() }),
    OPEN_JUV(Genre.OPEN, Age.JUVENILE, "Juvenil", { player, clock -> player.isActive && player.isJunior(clock) }),
    FEM_JUV(Genre.FEMALE, Age.JUVENILE, "Juvenil femenino", { player, clock -> player.isFemale() && player.isJunior(clock) }),
}

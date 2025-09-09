package org.github.javiermf.fide2wikipedia.domain.model

enum class GameStyle(
    val title: String,
    val writingName: String
){
    OPEN("Estándar", "ajedrez"),
    RAPID("Rápido", "ajedrez rápido"),
    BULLET("Relámpago", "ajedrez relámpago");
}

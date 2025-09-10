package org.github.javiermf.fide2wikipedia.domain.model

import java.time.Clock
import java.time.LocalDate

data class Player(
    val name: String,
    val country: String,
    val sex: String,
    val rating: Int,
    val birthday: Int,
    val isActive: Boolean = true
) {
    fun isFemale() = sex == "F"
    fun isJunior(clock: Clock): Boolean {
        val currentYear = LocalDate.now(clock).year
        val juniorLimitYear = currentYear - 21
        return birthday > juniorLimitYear
    }
    fun fullName () = name
}

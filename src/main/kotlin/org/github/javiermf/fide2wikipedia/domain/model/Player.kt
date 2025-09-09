package org.github.javiermf.fide2wikipedia.domain.model

import java.time.LocalDate

data class Player(
    val name: String,
    val country: String,
    val sex: String,
    val rating: Int,
    val birthday: Int
) {
    fun isFemale() = sex == "F" // Assuming 'F' for female, adjust if needed
    fun isActive() = true // Placeholder, actual logic might depend on more data
    fun isJunior(): Boolean {
        val currentYear = LocalDate.now().year
        val juniorLimitYear = currentYear - 21
        return birthday > juniorLimitYear
    }
    fun fullName () = name // Assuming name is already full name
}
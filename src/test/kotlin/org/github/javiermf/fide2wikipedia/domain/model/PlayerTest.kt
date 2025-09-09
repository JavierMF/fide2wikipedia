package org.github.javiermf.fide2wikipedia.domain.model

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.time.LocalDate

class PlayerTest {

    @Test
    fun `isFemale returns true for female player`() {
        val player = Player("Anna", "RUS", "F", 2500, 1990)
        player.isFemale() shouldBe true
    }

    @Test
    fun `isFemale returns false for male player`() {
        val player = Player("Boris", "RUS", "M", 2700, 1985)
        player.isFemale() shouldBe false
    }

    @Test
    fun `isActive returns true`() {
        val player = Player("Carl", "USA", "M", 2800, 1990)
        player.isActive() shouldBe true
    }

    @Test
    fun `isJunior returns true for junior player`() {
        val currentYear = LocalDate.now().year
        val juniorPlayer = Player("Junior", "ESP", "M", 2000, currentYear - 10) // 10 years old
        juniorPlayer.isJunior() shouldBe true
    }

    @Test
    fun `isJunior returns false for adult player`() {
        val currentYear = LocalDate.now().year
        val adultPlayer = Player("Adult", "GER", "F", 2200, currentYear - 25) // 25 years old
        adultPlayer.isJunior() shouldBe false
    }

    @Test
    fun `fullName returns correct name`() {
        val player = Player("Magnus Carlsen", "NOR", "M", 2850, 1990)
        player.fullName() shouldBe "Magnus Carlsen"
    }
}
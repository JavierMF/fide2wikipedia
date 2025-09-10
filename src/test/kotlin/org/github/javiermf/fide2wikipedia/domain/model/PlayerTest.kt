package org.github.javiermf.fide2wikipedia.domain.model

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.time.Clock
import java.time.Instant
import java.time.ZoneId

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
        player.isActive shouldBe true
    }

    @Test
    fun `isJunior returns true for junior player`() {
        val clock = Clock.fixed(Instant.parse("2024-01-01T10:00:00Z"), ZoneId.of("UTC"))
        val juniorPlayer = Player("Junior", "ESP", "M", 2000, 2014) // 10 years old in 2024
        juniorPlayer.isJunior(clock) shouldBe true
    }

    @Test
    fun `isJunior returns false for adult player`() {
        val clock = Clock.fixed(Instant.parse("2024-01-01T10:00:00Z"), ZoneId.of("UTC"))
        val adultPlayer = Player("Adult", "GER", "F", 2200, 1999) // 25 years old in 2024
        adultPlayer.isJunior(clock) shouldBe false
    }

    @Test
    fun `fullName returns correct name`() {
        val player = Player("Magnus Carlsen", "NOR", "M", 2850, 1990)
        player.fullName() shouldBe "Magnus Carlsen"
    }
}

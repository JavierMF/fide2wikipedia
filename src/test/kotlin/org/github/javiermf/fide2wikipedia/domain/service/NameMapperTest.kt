package org.github.javiermf.fide2wikipedia.domain.service

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class NameMapperTest {

    private val nameMapper = NameMapper()

    @Test
    fun `mapName returns mapped name if mapping exists`() {
        nameMapper.mapName("Liren Ding") shouldBe "Ding Liren"
        nameMapper.mapName("Erigaisi Arjun") shouldBe "Arjun Erigaisi"
    }

    @Test
    fun `mapName returns original name if no mapping exists`() {
        nameMapper.mapName("NonExistent Player") shouldBe "NonExistent Player"
        nameMapper.mapName("Magnus Carlsen") shouldBe "Magnus Carlsen"
    }
}
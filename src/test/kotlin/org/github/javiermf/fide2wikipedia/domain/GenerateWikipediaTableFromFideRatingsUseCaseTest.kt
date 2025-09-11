package org.github.javiermf.fide2wikipedia.domain

import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import org.github.javiermf.fide2wikipedia.domain.service.OutputFileWriter
import org.github.javiermf.fide2wikipedia.infrastructure.downloader.FideFilesDownloader
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.time.Clock
import java.time.Instant
import java.time.ZoneId

@ExtendWith(MockKExtension::class)
class GenerateWikipediaTableFromFideRatingsUseCaseTest {

    @MockK
    private lateinit var downloader: FideFilesDownloader

    @MockK
    private lateinit var outputWriter: OutputFileWriter

    @TempDir
    private lateinit var tempDir: File

    @Test
    fun `generate creates wikipedia table`() {
        // Given
        val clock = Clock.fixed(Instant.parse("2024-01-01T10:00:00Z"), ZoneId.of("UTC"))
        val useCase = GenerateWikipediaTableFromFideRatingsUseCase(downloader, outputWriter, clock)

        val standardFile = File(tempDir, "standard_rating_list.xml")
        standardFile.writeText("""
            <playerslist>
                <player>
                    <name>Carlsen, Magnus</name>
                    <country>NOR</country>
                    <sex>M</sex>
                    <rating>2850</rating>
                    <birthday>1990</birthday>
                </player>
            </playerslist>
        """.trimIndent())

        val rapidFile = File(tempDir, "rapid_rating_list.xml")
        rapidFile.writeText("""
            <playerslist>
                <player>
                    <name>Caruana, Fabiano</name>
                    <country>USA</country>
                    <sex>M</sex>
                    <rating>2800</rating>
                    <birthday>1992</birthday>
                </player>
            </playerslist>
        """.trimIndent())

        val blitzFile = File(tempDir, "blitz_rating_list.xml")
        blitzFile.writeText("""
            <playerslist>
                <player>
                    <name>Nakamura, Hikaru</name>
                    <country>USA</country>
                    <sex>M</sex>
                    <rating>2900</rating>
                    <birthday>1987</birthday>
                </player>
            </playerslist>
        """.trimIndent())

        every { downloader.downloadAndUnzipFideFiles() } returns tempDir.absolutePath
        val contentSlot = slot<String>()
        val capturedContent = mutableListOf<String>()
        every { outputWriter.writeContent(capture(contentSlot)) } answers {
            capturedContent.add(contentSlot.captured)
        }

        // When
        useCase.generate()

        // Then
        val expectedContent = """
=== <u>Estándar</u> ===
==== Abierto ====
Los 20 primeros jugadores de ajedrez del mundo en enero de 2024.<ref name=":0">{{Cita web|url=https://ratings.fide.com/top_lists.phtml|título=FIDE Ratings|fechaacceso=01-01-2024|sitioweb=ratings.fide.com}}</ref>

{| class="wikitable"
|-
! Posición
! Jugador
! Federación
! ELO
|-
! 1
| [[Magnus Carlsen]]
| {{NOR}}
| style="text-align:center" |2850
|}

==== Femenino ====
Las 20 primeras jugadoras de ajedrez del mundo en enero de 2024.<ref name=":0">{{Cita web|url=https://ratings.fide.com/top_lists.phtml|título=FIDE Ratings|fechaacceso=01-01-2024|sitioweb=ratings.fide.com}}</ref>

{| class="wikitable"
|-
! Posición
! Jugador
! Federación
! ELO
|}

==== Juvenil ====
Los 20 primeros jugadores juveniles de ajedrez del mundo en enero de 2024.<ref name=":0">{{Cita web|url=https://ratings.fide.com/top_lists.phtml|título=FIDE Ratings|fechaacceso=01-01-2024|sitioweb=ratings.fide.com}}</ref>

{| class="wikitable"
|-
! Posición
! Jugador
! Federación
! ELO
|}

==== Juvenil femenino ====
Las 20 primeras jugadoras juveniles de ajedrez del mundo en enero de 2024.<ref name=":0">{{Cita web|url=https://ratings.fide.com/top_lists.phtml|título=FIDE Ratings|fechaacceso=01-01-2024|sitioweb=ratings.fide.com}}</ref>

{| class="wikitable"
|-
! Posición
! Jugador
! Federación
! ELO
|}

=== <u>Rápido</u> ===
==== Abierto ====
Los 20 primeros jugadores de ajedrez rápido del mundo en enero de 2024.<ref name=":0">{{Cita web|url=https://ratings.fide.com/top_lists.phtml|título=FIDE Ratings|fechaacceso=01-01-2024|sitioweb=ratings.fide.com}}</ref>

{| class="wikitable"
|-
! Posición
! Jugador
! Federación
! ELO
|-
! 1
| [[Fabiano Caruana]]
| {{USA}}
| style="text-align:center" |2800
|}

==== Femenino ====
Las 20 primeras jugadoras de ajedrez rápido del mundo en enero de 2024.<ref name=":0">{{Cita web|url=https://ratings.fide.com/top_lists.phtml|título=FIDE Ratings|fechaacceso=01-01-2024|sitioweb=ratings.fide.com}}</ref>

{| class="wikitable"
|-
! Posición
! Jugador
! Federación
! ELO
|}

==== Juvenil ====
Los 20 primeros jugadores juveniles de ajedrez rápido del mundo en enero de 2024.<ref name=":0">{{Cita web|url=https://ratings.fide.com/top_lists.phtml|título=FIDE Ratings|fechaacceso=01-01-2024|sitioweb=ratings.fide.com}}</ref>

{| class="wikitable"
|-
! Posición
! Jugador
! Federación
! ELO
|}

==== Juvenil femenino ====
Las 20 primeras jugadoras juveniles de ajedrez rápido del mundo en enero de 2024.<ref name=":0">{{Cita web|url=https://ratings.fide.com/top_lists.phtml|título=FIDE Ratings|fechaacceso=01-01-2024|sitioweb=ratings.fide.com}}</ref>

{| class="wikitable"
|-
! Posición
! Jugador
! Federación
! ELO
|}

=== <u>Relámpago</u> ===
==== Abierto ====
Los 20 primeros jugadores de ajedrez relámpago del mundo en enero de 2024.<ref name=":0">{{Cita web|url=https://ratings.fide.com/top_lists.phtml|título=FIDE Ratings|fechaacceso=01-01-2024|sitioweb=ratings.fide.com}}</ref>

{| class="wikitable"
|-
! Posición
! Jugador
! Federación
! ELO
|-
! 1
| [[Hikaru Nakamura]]
| {{USA}}
| style="text-align:center" |2900
|}

==== Femenino ====
Las 20 primeras jugadoras de ajedrez relámpago del mundo en enero de 2024.<ref name=":0">{{Cita web|url=https://ratings.fide.com/top_lists.phtml|título=FIDE Ratings|fechaacceso=01-01-2024|sitioweb=ratings.fide.com}}</ref>

{| class="wikitable"
|-
! Posición
! Jugador
! Federación
! ELO
|}

==== Juvenil ====
Los 20 primeros jugadores juveniles de ajedrez relámpago del mundo en enero de 2024.<ref name=":0">{{Cita web|url=https://ratings.fide.com/top_lists.phtml|título=FIDE Ratings|fechaacceso=01-01-2024|sitioweb=ratings.fide.com}}</ref>

{| class="wikitable"
|-
! Posición
! Jugador
! Federación
! ELO
|}

==== Juvenil femenino ====
Las 20 primeras jugadoras juveniles de ajedrez relámpago del mundo en enero de 2024.<ref name=":0">{{Cita web|url=https://ratings.fide.com/top_lists.phtml|título=FIDE Ratings|fechaacceso=01-01-2024|sitioweb=ratings.fide.com}}</ref>

{| class="wikitable"
|-
! Posición
! Jugador
! Federación
! ELO
|}


""".trimIndent()
        capturedContent.joinToString("") shouldBe expectedContent
    }
}

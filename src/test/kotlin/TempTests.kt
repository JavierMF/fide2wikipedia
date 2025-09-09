import io.kotest.matchers.shouldBe
import org.github.javiermf.fide2wikipedia.domain.model.GameStyle
import org.github.javiermf.fide2wikipedia.adapter.output.wikipedia.SectionDefinition
import org.github.javiermf.fide2wikipedia.adapter.output.wikipedia.WikiTableWriter
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class TempTests {

    @Test
    fun `format table desc`() {
        val monthYearFormat = DateTimeFormatter.ofPattern("MMMM 'de' yyyy", Locale.forLanguageTag("ES"))
        val monthYearDesc = monthYearFormat.format(LocalDateTime.now())

        val openOpenDesc = WikiTableWriter.sectionDescGenerator(SectionDefinition.OPEN_TOP, GameStyle.OPEN)
        openOpenDesc shouldBe "Los 20 primeros jugadores de ajedrez del mundo en $monthYearDesc."

        val femRapidDesc = WikiTableWriter.sectionDescGenerator(SectionDefinition.FEM_TOP, GameStyle.RAPID)
        femRapidDesc shouldBe "Las 20 primeras jugadoras de ajedrez rápido del mundo en $monthYearDesc."

        val juvBulletDesc = WikiTableWriter.sectionDescGenerator(SectionDefinition.OPEN_JUV, GameStyle.BULLET)
        juvBulletDesc shouldBe "Los 20 primeros jugadores juveniles de ajedrez relámpago del mundo en $monthYearDesc."

        val femJuvBulletDesc = WikiTableWriter.sectionDescGenerator(SectionDefinition.FEM_JUV, GameStyle.BULLET)
        femJuvBulletDesc shouldBe "Las 20 primeras jugadoras juveniles de ajedrez relámpago del mundo en $monthYearDesc."
    }
}

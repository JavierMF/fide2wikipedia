import io.kotest.matchers.shouldBe
import org.github.javiermf.fide2wikipedia.GameStyle
import org.github.javiermf.fide2wikipedia.SectionDefinition
import org.github.javiermf.fide2wikipedia.WikiTableWriter
import org.junit.jupiter.api.Test

class TempTests {

    @Test
    fun `format table desc`() {
        val openOpenDesc = WikiTableWriter.sectionDescGenerator(SectionDefinition.OPEN_TOP, GameStyle.OPEN)
        openOpenDesc shouldBe "Los 20 primeros jugadores de ajedrez del mundo en septiembre de 2025."

        val femRapidDesc = WikiTableWriter.sectionDescGenerator(SectionDefinition.FEM_TOP, GameStyle.RAPID)
        femRapidDesc shouldBe "Las 20 primeras jugadoras de ajedrez rápido del mundo en septiembre de 2025."

        val juvBulletDesc = WikiTableWriter.sectionDescGenerator(SectionDefinition.OPEN_JUV, GameStyle.BULLET)
        juvBulletDesc shouldBe "Los 20 primeros jugadores juveniles de ajedrez relámpago del mundo en septiembre de 2025."

        val femJuvBulletDesc = WikiTableWriter.sectionDescGenerator(SectionDefinition.FEM_JUV, GameStyle.BULLET)
        femJuvBulletDesc shouldBe "Las 20 primeras jugadoras juveniles de ajedrez relámpago del mundo en septiembre de 2025."
    }
}

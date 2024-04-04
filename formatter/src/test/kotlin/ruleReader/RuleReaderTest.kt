package ruleReader

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RuleReaderTest {
    @Test
    fun test001_getAllFalseYamlData() {
        val ruleReader = RuleReader("src/test/resources/test001Formatter.yaml")
        val map = ruleReader.readFile()
        val expectedFormatting =
            mapOf(
                "colonBefore" to false,
                "colonAfter" to false,
                "assignationBefore" to false,
                "assignationAfter" to false,
                "printJump" to 0,
            )
        assertEquals(expectedFormatting, map)
    }

    @Test
    fun test002_getAllTrueYamlData() {
        val ruleReader = RuleReader("src/test/resources/test002Formatter.yaml")
        val map = ruleReader.readFile()
        val expectedFormatting =
            mapOf(
                "colonBefore" to true,
                "colonAfter" to true,
                "assignationBefore" to true,
                "assignationAfter" to true,
                "printJump" to 1,
            )
        assertEquals(expectedFormatting, map)
    }

    @Test
    fun test003_getMixedYamlData() {
        val ruleReader = RuleReader("src/test/resources/test003Formatter.yaml")
        val map = ruleReader.readFile()
        val expectedFormatting =
            mapOf(
                "colonBefore" to true,
                "colonAfter" to false,
                "assignationBefore" to true,
                "assignationAfter" to false,
                "printJump" to 2,
            )
        assertEquals(expectedFormatting, map)
    }
}

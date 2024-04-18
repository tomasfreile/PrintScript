package ruleReader

import formatter.formatOperations.commons.FileDataChecker
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class FileDataCheckerTest {
    @Test
    fun test001_testInvalidBooleanData() {
        val ruleReader = RuleReader("src/test/resources/invalidFormatterTest.yaml")
        val map = ruleReader.readFile()
        val fileDataChecker = FileDataChecker()
        assertThrows<NullPointerException> { fileDataChecker.checkBooleanData(map, "assignationAfter") }
    }

    @Test
    fun test002_testInvalidNumberData() {
        val ruleReader = RuleReader("src/test/resources/invalidFormatterTest.yaml")
        val map = ruleReader.readFile()
        val fileDataChecker = FileDataChecker()
        assertThrows<NullPointerException> { fileDataChecker.checkNumberData(map, "printJump") }
    }

    @Test
    fun test003_testValidBooleanData() {
        val ruleReader = RuleReader("src/test/resources/formatterTest01.yaml")
        val map = ruleReader.readFile()
        val fileDataChecker = FileDataChecker()
        assertEquals(fileDataChecker.checkBooleanData(map, "colonAfter"), true)
    }

    @Test
    fun test004_testValidNumberData() {
        val ruleReader = RuleReader("src/test/resources/formatterTest01.yaml")
        val map = ruleReader.readFile()
        val fileDataChecker = FileDataChecker()
        assertEquals(fileDataChecker.checkNumberData(map, "printJump"), 1)
    }
}

package sca

import sca.rules.getDefaultYamlValues
import sca.rules.readYaml
import kotlin.test.Test

class YamlReaderTest {
    @Test
    fun emptyFileShouldReturnDefaultValues() {
        val result = readYaml("C:\\Users\\tomyf\\ingisis\\PrintScript\\sca\\src\\test\\resources\\empty.yaml")
        assert(result == getDefaultYamlValues())
    }

    @Test
    fun nonExistentFileShouldReturnDefaultValues() {
        val result = readYaml("non_existent.yaml")
        assert(result == getDefaultYamlValues())
    }

    @Test
    fun wrongFormatFileShouldReturnDefaultValues() {
        val result = readYaml("C:\\Users\\tomyf\\ingisis\\PrintScript\\sca\\src\\test\\resources\\wrong_format.yaml")
        assert(result == getDefaultYamlValues())
    }

    @Test
    fun differentExtensionFileShouldReturnDefaultValues() {
        val result = readYaml("C:\\Users\\tomyf\\ingisis\\PrintScript\\sca\\src\\test\\resources\\different_extension.json")
        assert(result == getDefaultYamlValues())
    }

    @Test
    fun snakeCaseFileShouldReturnSnakeCase() {
        val result = readYaml("C:\\Users\\tomyf\\ingisis\\PrintScript\\sca\\src\\test\\resources\\PrintExpressionsAndSnakeCase.yaml")
        assert(result["caseConvention"] == "SNAKE_CASE")
        assert(result["enablePrintExpressions"] == true)
    }

    @Test
    fun camelCaseNoExpressionsFileShouldReturnCamelCase() {
        val result = readYaml("C:\\Users\\tomyf\\ingisis\\PrintScript\\sca\\src\\test\\resources\\NoPrintExpressionsAndCamelCase.yaml")
        assert(result["caseConvention"] == "CAMEL_CASE")
        assert(result["enablePrintExpressions"] == false)
    }
}

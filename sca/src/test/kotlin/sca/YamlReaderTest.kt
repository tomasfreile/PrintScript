package sca

import java.io.FileNotFoundException
import kotlin.test.Test
import kotlin.test.assertFailsWith

class YamlReaderTest {
    private val yamlReader = YamlReader("1.0")

    @Test
    fun emptyFileShouldReturnDefaultValues() {
        val result = yamlReader.readYaml("src/test/resources/empty.yaml")
        assert(result.isEmpty())
    }

    @Test
    fun nonExistentFileShouldThrowFileNotFoundException() {
        assertFailsWith<FileNotFoundException> {
            yamlReader.readYaml("src/test/resources/non_existent.yaml")
        }
    }

    @Test
    fun wrongFormatFileShouldReturnDefaultValues() {
        val result = yamlReader.readYaml("src/test/resources/wrong_format.yaml")
        assert(result.isEmpty())
    }

    @Test
    fun differentExtensionFileShouldThrowFileNotFoundException() {
        val result = yamlReader.readYaml("src/test/resources/different_extension.json")
        assert(result.isEmpty())
    }

    @Test
    fun snakeCaseFileShouldReturnSnakeCase() {
        val result =
            yamlReader.readYaml(
                "src/test/resources/PrintExpressionsAndSnakeCase.yaml",
            )
        assert(result["caseConvention"] == "SNAKE_CASE")
        assert(result["enablePrintExpressions"] == true)
    }

    @Test
    fun camelCaseNoExpressionsFileShouldReturnCamelCase() {
        val result =
            yamlReader.readYaml(
                "src/test/resources/NoPrintExpressionsAndCamelCase.yaml",
            )
        assert(result["caseConvention"] == "CAMEL_CASE")
        assert(result["enablePrintExpressions"] == false)
    }
}

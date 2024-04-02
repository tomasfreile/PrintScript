package sca

import kotlin.test.Test

class YamlReaderTest {
    val yamlReader = YamlReader()

    @Test
    fun emptyFileShouldReturnDefaultValues() {
        val result = yamlReader.readYaml("src/test/resources/empty.yaml")
        assert(result == yamlReader.getDefaultYamlValues())
    }

    @Test
    fun nonExistentFileShouldReturnDefaultValues() {
        val result = yamlReader.readYaml("non_existent.yaml")
        assert(result == yamlReader.getDefaultYamlValues())
    }

    @Test
    fun wrongFormatFileShouldReturnDefaultValues() {
        val result = yamlReader.readYaml("src/test/resources/wrong_format.yaml")
        assert(result == yamlReader.getDefaultYamlValues())
    }

    @Test
    fun differentExtensionFileShouldReturnDefaultValues() {
        val result = yamlReader.readYaml("src/test/resources/different_extension.txt")
        assert(result == yamlReader.getDefaultYamlValues())
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

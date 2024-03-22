package sca

import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.error.YAMLException
import sca.rules.NamingConvention
import sca.rules.PrintShouldNotContainExpressions
import sca.rules.Rule
import sca.rules.VariableNamingRule
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException

class YamlReader {
    fun readYaml(fileName: String): Map<String, Any> {
        if (!fileName.endsWith(".yaml") && !fileName.endsWith(".yml")) {
            // If the file has a different extension, return default values directly
            println("File '$fileName' has a different extension. Using default YAML values.")
            return getDefaultYamlValues()
        }

        val yaml = Yaml()
        val file = File(fileName)

        try {
            val inputStream = FileInputStream(file)
            return inputStream.use { stream ->
                yaml.load(stream) as? Map<String, Any> ?: getDefaultYamlValues()
            }
        } catch (e: FileNotFoundException) {
            // Log or handle the FileNotFoundException
            println("File '$fileName' not found. Using default YAML values.")
            return getDefaultYamlValues()
        } catch (e: YAMLException) {
            // Log or handle the YAMLException
            println("Error parsing YAML file '$fileName'. Using default YAML values.")
            return getDefaultYamlValues()
        }
    }

    fun getDefaultYamlValues(): Map<String, Any> {
        return mapOf(
            "enablePrintExpressions" to false,
            "caseConvention" to "CAMEL_CASE",
        )
    }

    fun readRules(fileName: String): List<Rule> {
        val yamlMap = readYaml(fileName)
        val rules = mutableListOf<Rule>()

        // Extracting values from the YAML map
        val enablePrintExpressions = yamlMap["enablePrintExpressions"] as? Boolean ?: false
        val caseConvention =
            yamlMap["caseConvention"]?.toString()?.let {
                when (it) {
                    "SNAKE_CASE" -> NamingConvention.SNAKE_CASE
                    "CAMEL_CASE" -> NamingConvention.CAMEL_CASE
                    else -> null
                }
            } ?: NamingConvention.CAMEL_CASE // Default to CAMEL_CASE if value not found or invalid

        // Adding rules based on the extracted values
        if (!enablePrintExpressions) {
            rules.add(PrintShouldNotContainExpressions())
        }
        rules.add(VariableNamingRule(caseConvention))

        return rules
    }
}

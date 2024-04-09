@file:Suppress("ktlint:standard:no-wildcard-imports")

package sca

import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.error.YAMLException
import sca.rules.*
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.util.*

class YamlReader {
    private val defaultValues: Map<String, Any> = emptyMap()

    fun readYaml(fileName: String): Map<String, Any> {
        val file = File(fileName)

        if (!file.exists()) {
            throw FileNotFoundException("File '$fileName' not found.")
        }

        if (!file.extension.equals("yaml", ignoreCase = true) && !file.extension.equals("yml", ignoreCase = true)) {
            println("File '$fileName' has a different extension. Using default YAML values.")
            return defaultValues
        }

        val yaml = Yaml()

        return try {
            val inputStream = FileInputStream(file)
            inputStream.use { stream ->
                yaml.load(stream) as? Map<String, Any> ?: defaultValues
            }
        } catch (e: YAMLException) {
            throw IllegalArgumentException("Error parsing YAML file '$fileName'.", e)
        }
    }

    fun readRules(fileName: String): List<Rule> {
        val yamlMap = readYaml(fileName)

        return listOfNotNull(
            if (yamlMap["enablePrintExpressions"] as? Boolean == false) PrintShouldNotContainExpressions() else null,
            if (yamlMap["enableReadInputExpressions"] as? Boolean == false) ReadInputShouldNotContainExpressions() else null,
            yamlMap["caseConvention"]?.toString()?.uppercase(Locale.getDefault())?.let { convention ->
                when (convention) {
                    "SNAKE_CASE" -> NamingConvention.SNAKE_CASE
                    "CAMEL_CASE" -> NamingConvention.CAMEL_CASE
                    else -> null
                }?.let { VariableNamingRule(it) }
            },
        )
    }
}

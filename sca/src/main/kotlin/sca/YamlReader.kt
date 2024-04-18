@file:Suppress("ktlint:standard:no-wildcard-imports")

package sca

import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.error.YAMLException
import sca.rules.Rule
import sca.rules.matcher.getRuleMatchers
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException

class YamlReader(private val version: String) {
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
        val ruleMatchers = getRuleMatchers(version)
        val rules = mutableListOf<Rule>()
        for (matcher in ruleMatchers) {
            val rule = matcher.addRule(yamlMap)
            if (rule != null) {
                rules.add(rule)
            }
        }
        return rules
    }
}

package ruleReader

import org.yaml.snakeyaml.Yaml
import java.io.File
import java.io.FileInputStream

class RuleReader(private val rulesPath: String) {
    fun readFile(): Map<String, Any> {
        val yaml = Yaml()
        val inputStream = FileInputStream(File(rulesPath))
        return inputStream.use { stream ->
            yaml.load(stream)
        }
    }
}

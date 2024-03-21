package formatter


import ast.ASTBinaryNode
import ast.ASTSingleNode
import ast.Node
import org.yaml.snakeyaml.Yaml
import java.io.File
import java.io.FileInputStream

class Formatter() {

    fun format(node: Node): String{
        val yamlData = readYaml()
        return recursiveFormat("",yamlData, node)
    }

    private fun recursiveFormat(result: String, yamlData: Map<String, Any>, node: Node): String{
        val token = node.token
        return when(node){
            is ASTSingleNode -> { handleLiteral() }
            is ASTBinaryNode -> { handleBinary() }
            else -> { return "i don't know dude. It shouldn´t had happened"}
        }
    }

    private fun handleLiteral(): String{
        return "single"
    }

    private fun handleBinary(): String{
        return "I am binary"
    }

    fun readYaml(): Map<String,Any> {
        val yaml = Yaml()
        val inputStream = FileInputStream(File("src/main/resources/formatter.yaml"))
        return inputStream.use { stream ->
            yaml.load(stream)
        }
    }
}
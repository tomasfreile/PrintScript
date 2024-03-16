package org.example.formatter

import org.example.parser.ASTBinaryNode
import org.example.parser.ASTSingleNode
import org.example.parser.Node
import org.example.token.TypeEnum
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
        val inputStream = FileInputStream(File("/home/marcos/Documentos/Projects/IngSis/PrintScript/src/main/kotlin/formatter/formatter.yaml"))
        return inputStream.use { stream ->
            yaml.load(stream)
        }
    }
}
package formatter


import ast.ASTBinaryNode
import ast.ASTSingleNode
import ast.Node
import org.yaml.snakeyaml.Yaml
import token.Token
import token.TokenType
import java.io.File
import java.io.FileInputStream

class Formatter() {

    fun format(node: Node): String{
        val yamlData = readYaml()
        return recursiveFormat("",yamlData, node)
    }

    private fun recursiveFormat(result: String, yamlData: Map<String, Any>, node: Node?): String{
        return when(node){
            is ASTSingleNode -> handleLiteral(result, yamlData, node)
            is ASTBinaryNode -> handleBinary(result, yamlData, node)
            else -> return result
        }
    }

    private fun handleLiteral(result: String, yamlData: Map<String, Any>, node: ASTSingleNode): String{
        val token = node.token
        val formattedString = result + formatToken(token, yamlData)
        return recursiveFormat(formattedString, yamlData, node.node)
    }

    private fun handleBinary(result: String, yamlData: Map<String, Any>, node: ASTBinaryNode): String{
        val token = node.token
        val formattedNode = formatToken(token, yamlData)
        val formattedString = recursiveFormat(result, yamlData, node.left) + formattedNode + recursiveFormat(result, yamlData, node.right)
        return formattedString
    }

    fun readYaml(): Map<String,Any> {
        val yaml = Yaml()
        val inputStream = FileInputStream(File("src/main/resources/formatter.yaml"))
        return inputStream.use { stream ->
            yaml.load(stream)
        }
    }

    private fun formatToken(token: Token, yamlData: Map<String, Any>) : String{
        val tokenValue = token.value
        return when(token.type){
            TokenType.COLON -> formatColon(tokenValue, yamlData)
            TokenType.ASSIGNATION -> formatEquals(tokenValue, yamlData)
            TokenType.PRINT -> formatPrintln(tokenValue, yamlData)
            TokenType.PLUS -> formatOperator(tokenValue)
            TokenType.MINUS -> formatOperator(tokenValue)
            TokenType.STAR -> formatOperator(tokenValue)
            TokenType.SEMICOLON -> formatSemicolon(tokenValue)
            else -> formatRegularToken(tokenValue)
        }
    }

    private fun formatColon(tokenValue: String, yamlData: Map<String, Any>) : String{
        val colonBefore = yamlData["colonBefore"] as Boolean
        val colonAfter = yamlData["colonAfter"] as Boolean
        return "${if (colonBefore) " " else ""}$tokenValue${if (colonAfter) " " else ""}"
    }
    private fun formatEquals(tokenValue: String, yamlData: Map<String, Any>) : String{
        val equalsBefore = yamlData["equalsBefore"] as Boolean
        val equalsAfter = yamlData["equalsAfter"] as Boolean
        return "${if (equalsBefore) " " else ""}$tokenValue${if (equalsAfter) " " else ""}"
    }
    private fun formatPrintln(tokenValue: String, yamlData: Map<String, Any>) : String{
        val printJump = yamlData["printJump"] as Int
        return "\n".repeat(printJump) + tokenValue
    }
    private fun formatSemicolon(tokenValue: String) : String{
        return tokenValue + "\n"
    }

    private fun formatOperator(tokenValue: String) : String{
        return " $tokenValue "
    }

    private fun formatRegularToken(tokenValue: String) : String{
        return " $tokenValue"
    }
}
package org.example.parser.semantic

import org.example.token.Token
import org.example.token.TokenType

class NumberTypeDeclaration: SemanticChecker {
    override fun checkSemantic(tokenList: List<Token>): Boolean {
        for(token in tokenList){
            when(token.type){
                TokenType.STRING -> return false
                else -> { continue }
            }
        }
        return true
    }
}
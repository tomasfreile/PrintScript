package org.example.parser.semantic

import token.Token
import token.TokenType

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
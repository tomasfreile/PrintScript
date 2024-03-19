package sca

sealed class StaticCodeAnalyzerResult {
    data class Error(val message: String) : StaticCodeAnalyzerResult()
    data object Ok : StaticCodeAnalyzerResult()
}
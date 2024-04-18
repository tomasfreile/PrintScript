package sca.rules.matcher

fun getRuleMatchers(version: String): List<RuleMatcher> {
    return when (version) {
        "1.0" -> listOf(CaseConventionRuleMatcher(), PrintRuleMatcher())
        "1.1" -> listOf(CaseConventionRuleMatcher(), PrintRuleMatcher(), ReadInputRuleMatcher())
        else -> throw IllegalArgumentException("Invalid version: $version. Supported versions are 1.0 and 1.1")
    }
}

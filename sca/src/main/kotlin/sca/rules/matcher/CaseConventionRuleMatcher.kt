@file:Suppress("ktlint:standard:no-wildcard-imports")

package sca.rules.matcher

import sca.rules.NamingConvention
import sca.rules.Rule
import sca.rules.VariableNamingRule
import java.util.*

class CaseConventionRuleMatcher : RuleMatcher {
    override fun addRule(yamlMap: Map<String, Any>): Rule? {
        val rule: Rule? =
            yamlMap["caseConvention"]?.toString()?.uppercase(Locale.getDefault())?.let { convention ->
                when (convention) {
                    "SNAKE_CASE" -> NamingConvention.SNAKE_CASE
                    "CAMEL_CASE" -> NamingConvention.CAMEL_CASE
                    else -> null
                }?.let { VariableNamingRule(it) }
            }
        if (rule != null) {
            return rule
        }
        return null
    }
}

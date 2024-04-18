package sca.rules.matcher

import sca.rules.PrintShouldNotContainExpressions
import sca.rules.Rule

class PrintRuleMatcher : RuleMatcher {
    override fun addRule(yamlMap: Map<String, Any>): Rule? {
        val rule: Rule? =
            if (yamlMap["enablePrintExpressions"] as? Boolean == false) {
                PrintShouldNotContainExpressions()
            } else {
                null
            }
        if (rule != null) {
            return rule
        }
        return null
    }
}

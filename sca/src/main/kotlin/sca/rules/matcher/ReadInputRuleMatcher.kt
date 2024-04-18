package sca.rules.matcher

import sca.rules.ReadInputShouldNotBePartOfExpression
import sca.rules.Rule

class ReadInputRuleMatcher : RuleMatcher {
    override fun addRule(yamlMap: Map<String, Any>): Rule? {
        val rule: Rule? =
            if (yamlMap["enableReadInputExpressions"] as? Boolean == false) {
                ReadInputShouldNotBePartOfExpression()
            } else {
                null
            }
        if (rule != null) {
            return rule
        }
        return null
    }
}

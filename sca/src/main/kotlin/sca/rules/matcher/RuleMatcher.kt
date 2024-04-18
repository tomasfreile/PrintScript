package sca.rules.matcher

import sca.rules.Rule

interface RuleMatcher {
    fun addRule(yamlMap: Map<String, Any>): Rule?
}

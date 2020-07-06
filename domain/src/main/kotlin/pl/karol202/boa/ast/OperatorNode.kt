package pl.karol202.boa.ast

import pl.karol202.boa.syntax.OperatorType

data class OperatorNode(val type: OperatorType) : InvocableNode

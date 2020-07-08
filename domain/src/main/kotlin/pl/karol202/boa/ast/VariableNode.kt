package pl.karol202.boa.ast

import pl.karol202.boa.syntax.VariableType

data class VariableNode(val type: VariableType,
                        val identifier: IdentifierNode,
                        val value: ExpressionNode) : StatementNode

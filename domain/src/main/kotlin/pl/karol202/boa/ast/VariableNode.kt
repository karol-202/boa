package pl.karol202.boa.ast

import pl.karol202.boa.syntax.VariableMutability

data class VariableNode(val mutability: VariableMutability,
                        val identifier: IdentifierNode,
                        val type: IdentifierNode?,
                        val value: ExpressionNode) : StatementNode

package pl.karol202.boa.ast

enum class VariableType
{
	IMMUTABLE,
	MUTABLE
}

data class VariableNode(val type: VariableType,
                        val identifier: IdentifierNode,
                        val value: ExpressionNode) : StatementNode

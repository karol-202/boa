package pl.karol202.boa.ast

enum class Operator
{
	UNARY_PLUS,
	UNARY_MINUS,

	PLUS,
	MINUS
}

data class OperatorNode(val operator: Operator) : InvocableNode

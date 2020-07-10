package pl.karol202.boa.ast

sealed class LiteralNode : ExpressionNode
{
	data class StringLiteral(override val value: String) : LiteralNode()
	data class IntegerLiteral(override val value: Int) : LiteralNode()
	data class RealLiteral(override val value: Double) : LiteralNode()

	abstract val value: Any
}

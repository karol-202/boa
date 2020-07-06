package pl.karol202.boa.ast

sealed class LiteralNode : ExpressionNode
{
	data class StringLiteral(val value: String) : LiteralNode()
	data class IntegerLiteral(val value: Int) : LiteralNode()
	data class RealLiteral(val value: Double) : LiteralNode()
}

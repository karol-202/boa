package pl.karol202.boa.ast

data class ArgumentsListNode(val parameters: List<ExpressionNode>) : Node
{
	constructor(vararg parameters: ExpressionNode?) : this(listOfNotNull(*parameters))
}

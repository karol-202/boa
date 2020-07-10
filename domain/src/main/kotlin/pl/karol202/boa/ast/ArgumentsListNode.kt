package pl.karol202.boa.ast

data class ArgumentsListNode(val list: List<ExpressionNode>) : Node
{
	constructor(vararg arguments: ExpressionNode?) : this(listOfNotNull(*arguments))
}

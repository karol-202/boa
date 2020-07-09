package pl.karol202.boa.ast

data class StatementSequenceNode(val statements: List<StatementNode>) : Node
{
	constructor(vararg statements: StatementNode?) : this(listOfNotNull(*statements))
}

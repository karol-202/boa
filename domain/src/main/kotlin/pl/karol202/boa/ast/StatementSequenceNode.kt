package pl.karol202.boa.ast

data class StatementSequenceNode(val list: List<StatementNode>) : Node
{
	constructor(vararg statements: StatementNode?) : this(listOfNotNull(*statements))
}

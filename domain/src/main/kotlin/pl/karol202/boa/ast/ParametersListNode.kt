package pl.karol202.boa.ast

data class ParametersListNode(val list: List<ParameterNode>) : Node
{
	constructor(vararg parameters: ParameterNode?) : this(listOfNotNull(*parameters))
}

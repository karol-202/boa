package pl.karol202.boa.ast

data class ParameterNode(val identifier: IdentifierNode,
                         val type: IdentifierNode) : Node

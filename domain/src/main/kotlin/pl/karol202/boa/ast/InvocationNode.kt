package pl.karol202.boa.ast

data class InvocationNode(val target: InvocableNode,
                          val arguments: ArgumentsListNode) : ExpressionNode

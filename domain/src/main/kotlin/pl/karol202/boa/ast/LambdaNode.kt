package pl.karol202.boa.ast

data class LambdaNode(val parameters: ParametersListNode,
                      val block: StatementSequenceNode) : ExpressionNode

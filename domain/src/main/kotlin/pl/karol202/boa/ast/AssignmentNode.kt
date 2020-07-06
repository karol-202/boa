package pl.karol202.boa.ast

data class AssignmentNode(val target: AssignmentTargetNode,
                          val value: ExpressionNode) : ExpressionNode

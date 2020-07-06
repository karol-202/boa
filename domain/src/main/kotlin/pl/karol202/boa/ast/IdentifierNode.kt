package pl.karol202.boa.ast

data class IdentifierNode(val name: String) : ExpressionNode, AssignmentTargetNode

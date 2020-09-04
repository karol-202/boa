package pl.karol202.boa.interpreter.handler

import pl.karol202.boa.ast.AssignmentTargetNode
import pl.karol202.boa.ast.IdentifierNode
import pl.karol202.boa.interpreter.InterpreterException
import pl.karol202.boa.interpreter.data.InterpreterContext
import pl.karol202.boa.interpreter.data.AssignmentTarget
import pl.karol202.boa.interpreter.data.VariableAssignmentTarget

object AssignmentTargetHandler : Handler<AssignmentTargetNode, AssignmentTarget>
{
	override fun InterpreterContext.handle(node: AssignmentTargetNode) = when(node)
	{
		is IdentifierNode -> withResult(VariableAssignmentTarget(node.name))
		else -> throw InterpreterException.UnexpectedNode(AssignmentTargetNode::class, node::class)
	}
}

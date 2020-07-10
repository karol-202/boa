package pl.karol202.boa.interpreter.handler

import pl.karol202.boa.ast.AssignmentTargetNode
import pl.karol202.boa.ast.IdentifierNode
import pl.karol202.boa.interpreter.InterpreterException
import pl.karol202.boa.interpreter.context.InterpreterContext

// TODO Introduce type for assignment targets
object AssignmentTargetHandler : Handler<AssignmentTargetNode, String>
{
	override fun InterpreterContext.handle(node: AssignmentTargetNode) = when(node)
	{
		is IdentifierNode -> withResult(node.name)
		else -> throw InterpreterException.UnexpectedNode(AssignmentTargetNode::class, node::class)
	}
}

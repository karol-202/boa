package pl.karol202.boa.interpreter.handler

import pl.karol202.boa.ast.AssignmentNode
import pl.karol202.boa.ast.ExpressionNode
import pl.karol202.boa.ast.StatementNode
import pl.karol202.boa.ast.VariableNode
import pl.karol202.boa.interpreter.InterpreterException
import pl.karol202.boa.interpreter.context.InterpreterContext

object StatementHandler : Handler<StatementNode, Unit>
{
	override fun InterpreterContext.handle(node: StatementNode) = when(node)
	{
		is VariableNode -> handle(VariableHandler, node)
		is AssignmentNode -> handle(AssignmentHandler, node)
		is ExpressionNode -> handle(ExpressionHandler, node) withResult Unit
		else -> throw InterpreterException.UnexpectedNode(StatementNode::class, node::class)
	}
}

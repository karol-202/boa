package pl.karol202.boa.interpreter.handler

import pl.karol202.boa.ast.*
import pl.karol202.boa.interpreter.InterpreterException
import pl.karol202.boa.interpreter.data.InterpreterContext

object StatementHandler : Handler<StatementNode, Unit>
{
	override fun InterpreterContext.handle(node: StatementNode) = when(node)
	{
		is VariableNode -> handle(VariableHandler, node)
		is AssignmentNode -> handle(AssignmentHandler, node)
		is ExpressionNode -> handle(ExpressionHandler, node) withResult Unit
		is ImportNode -> throw InterpreterException.NotSupported("Imports should be resolved in middleend level")
		else -> throw InterpreterException.UnexpectedNode(StatementNode::class, node::class)
	}
}

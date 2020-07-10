package pl.karol202.boa.interpreter.handler

import pl.karol202.boa.ast.ExpressionNode
import pl.karol202.boa.ast.InvocableNode
import pl.karol202.boa.ast.OperatorNode
import pl.karol202.boa.interpreter.InterpreterException
import pl.karol202.boa.interpreter.context.InterpreterContext
import pl.karol202.boa.interpreter.context.Invocable

object InvocableHandler : Handler<InvocableNode, Invocable>
{
	override fun InterpreterContext.handle(node: InvocableNode) = when(node)
	{
		is ExpressionNode -> handle(ExpressionHandler, node) then { value ->
			if(value !is Invocable) throw InterpreterException.TypeError(Invocable::class, value::class)
			else withResult(value)
		}
		is OperatorNode -> handle(OperatorHandler, node) then { withResult(it) }
		else -> throw InterpreterException.UnexpectedNode(InvocableNode::class, node::class)
	}
}

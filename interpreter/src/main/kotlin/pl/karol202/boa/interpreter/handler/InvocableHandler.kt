package pl.karol202.boa.interpreter.handler

import pl.karol202.boa.ast.ExpressionNode
import pl.karol202.boa.ast.InvocableNode
import pl.karol202.boa.ast.OperatorNode
import pl.karol202.boa.interpreter.InterpreterException
import pl.karol202.boa.interpreter.data.InterpreterContext
import pl.karol202.boa.interpreter.value.FunctionValue
import pl.karol202.boa.interpreter.value.requireToBeFunction
import pl.karol202.boa.type.FunctionType

object InvocableHandler : Handler<InvocableNode, FunctionValue>
{
	override fun InterpreterContext.handle(node: InvocableNode) = when(node)
	{
		is ExpressionNode -> handle(ExpressionHandler, node) then { value ->
			withResult(value.requireToBeFunction())
		}
		is OperatorNode -> handle(OperatorHandler, node) then { withResult(it) }
		else -> throw InterpreterException.UnexpectedNode(InvocableNode::class, node::class)
	}
}

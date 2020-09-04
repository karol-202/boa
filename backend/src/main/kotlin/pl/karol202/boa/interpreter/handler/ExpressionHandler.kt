package pl.karol202.boa.interpreter.handler

import pl.karol202.boa.ast.ExpressionNode
import pl.karol202.boa.ast.IdentifierNode
import pl.karol202.boa.ast.InvocationNode
import pl.karol202.boa.ast.LiteralNode
import pl.karol202.boa.interpreter.InterpreterException
import pl.karol202.boa.interpreter.data.InterpreterContext

object ExpressionHandler : Handler<ExpressionNode, Any>
{
	override fun InterpreterContext.handle(node: ExpressionNode) = when(node)
	{
		is IdentifierNode -> handle(IdentifierHandler, node)
		is LiteralNode -> handle(LiteralHandler, node)
		is InvocationNode -> handle(InvocationHandler, node)
		else -> throw InterpreterException.UnexpectedNode(ExpressionNode::class, node::class)
	}
}

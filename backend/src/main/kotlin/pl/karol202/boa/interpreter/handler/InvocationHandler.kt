package pl.karol202.boa.interpreter.handler

import pl.karol202.boa.ast.InvocationNode
import pl.karol202.boa.interpreter.context.InterpreterContext

object InvocationHandler : Handler<InvocationNode, Any>
{
	override fun InterpreterContext.handle(node: InvocationNode) =
		handle(InvocableHandler, node.target) then { invocable ->
			handle(ArgumentsListHandler, node.arguments) then { args ->
				withResult(invocable.invoke(this, args))
			}
		}
}

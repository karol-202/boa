package pl.karol202.boa.interpreter.handler

import pl.karol202.boa.ast.InvocationNode
import pl.karol202.boa.interpreter.data.InterpreterContext
import pl.karol202.boa.interpreter.value.Value

object InvocationHandler : Handler<InvocationNode, Value>
{
	override fun InterpreterContext.handle(node: InvocationNode) =
		handle(InvocableHandler, node.target) then { function ->
			handle(ArgumentsListHandler, node.arguments) then { args ->
				withResult(function.invoke(this, args))
			}
		}
}

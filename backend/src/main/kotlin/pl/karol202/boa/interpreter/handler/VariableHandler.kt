package pl.karol202.boa.interpreter.handler

import pl.karol202.boa.ast.VariableNode
import pl.karol202.boa.interpreter.context.InterpreterContext

object VariableHandler : Handler<VariableNode, Unit>
{
	override fun InterpreterContext.handle(node: VariableNode) =
		handle(ExpressionHandler, node.value) then { value ->
			withVariable(node.identifier.name, value) withResult Unit
		}
}

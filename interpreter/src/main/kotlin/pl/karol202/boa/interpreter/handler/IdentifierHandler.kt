package pl.karol202.boa.interpreter.handler

import pl.karol202.boa.ast.IdentifierNode
import pl.karol202.boa.interpreter.data.InterpreterContext
import pl.karol202.boa.interpreter.value.Value

object IdentifierHandler : Handler<IdentifierNode, Value>
{
	override fun InterpreterContext.handle(node: IdentifierNode) = withResult(requireVariableValue(node.name))
}

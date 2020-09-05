package pl.karol202.boa.interpreter.handler

import pl.karol202.boa.ast.IdentifierNode
import pl.karol202.boa.interpreter.InterpreterException
import pl.karol202.boa.interpreter.data.InterpreterContext

object IdentifierHandler : Handler<IdentifierNode, Any>
{
	override fun InterpreterContext.handle(node: IdentifierNode) =
		withResult(variables[node.name]?.value ?: throw InterpreterException.UnknownIdentifier(node.name))
}

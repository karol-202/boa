package pl.karol202.boa.interpreter.handler

import pl.karol202.boa.ast.LiteralNode
import pl.karol202.boa.interpreter.data.InterpreterContext

object LiteralHandler : Handler<LiteralNode, Any>
{
	override fun InterpreterContext.handle(node: LiteralNode) = withResult(node.value)
}

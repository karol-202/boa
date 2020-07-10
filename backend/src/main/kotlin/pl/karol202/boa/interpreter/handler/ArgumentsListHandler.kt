package pl.karol202.boa.interpreter.handler

import pl.karol202.boa.ast.ArgumentsListNode
import pl.karol202.boa.interpreter.context.InterpreterContext

object ArgumentsListHandler : Handler<ArgumentsListNode, List<Any>>
{
	override fun InterpreterContext.handle(node: ArgumentsListNode) =
		node.list.fold(withResult(emptyList<Any>())) { (context, values), expressionNode ->
			context.handle(ExpressionHandler, expressionNode) then { value ->
				withResult(values + value)
			}
		}
}

package pl.karol202.boa.interpreter.handler

import pl.karol202.boa.ast.ArgumentsListNode
import pl.karol202.boa.interpreter.data.InterpreterContext
import pl.karol202.boa.interpreter.value.Value

object ArgumentsListHandler : Handler<ArgumentsListNode, List<Value>>
{
	override fun InterpreterContext.handle(node: ArgumentsListNode) =
		node.list.fold(withResult(emptyList<Value>())) { (context, values), expressionNode ->
			context.handle(ExpressionHandler, expressionNode) then { value ->
				withResult(values + value)
			}
		}
}

package pl.karol202.boa.interpreter.handler

import pl.karol202.boa.ast.OperatorNode
import pl.karol202.boa.interpreter.context.InterpreterContext
import pl.karol202.boa.interpreter.context.Invocable
import pl.karol202.boa.syntax.OperatorType

object OperatorHandler : Handler<OperatorNode, Invocable>
{
	override fun InterpreterContext.handle(node: OperatorNode) = withResult(Invocable.create { args ->
		when(node.type)
		{
			// TODO Rewrite when the type system is ready
			OperatorType.UNARY_PLUS -> args[0]
			OperatorType.UNARY_MINUS -> -(args[0] as Int)
			OperatorType.PLUS -> (args[0] as Int) + (args[1] as Int)
			OperatorType.MINUS -> (args[0] as Int) - (args[1] as Int)
		}
	})
}

package pl.karol202.boa.interpreter.handler

import pl.karol202.boa.ast.StatementSequenceNode
import pl.karol202.boa.interpreter.data.InterpreterContext

object StatementSequenceHandler : Handler<StatementSequenceNode, Unit>
{
	override fun InterpreterContext.handle(node: StatementSequenceNode) = node.list.fold(this) { context, statement ->
		context.handle(StatementHandler, statement).context
	} withResult Unit
}

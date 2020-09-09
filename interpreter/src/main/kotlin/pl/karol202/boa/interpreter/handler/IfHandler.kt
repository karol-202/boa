package pl.karol202.boa.interpreter.handler

import pl.karol202.boa.ast.IfNode
import pl.karol202.boa.interpreter.data.InterpreterContext
import pl.karol202.boa.interpreter.value.BoolValue
import pl.karol202.boa.interpreter.value.requireToBe
import pl.karol202.boa.type.BoolType

object IfHandler : Handler<IfNode, Unit>
{
	override fun InterpreterContext.handle(node: IfNode): Handler.Result<Unit>
	{
		val (context, clause) = selectClause(node)
		return if(clause != null) context.handle(StatementSequenceHandler, clause.block)
		else context withResult Unit
	}

	private fun InterpreterContext.selectClause(node: IfNode): Pair<InterpreterContext, IfNode.Clause?>
	{
		val newContext = node.conditionClauses.fold(this) { context, clause ->
			val (newContext, enter) = context.handle(ExpressionHandler, clause.condition)
			if(enter.requireToBe<BoolValue>(BoolType).value) return newContext to clause
			newContext
		}
		return newContext to node.fallbackClause
	}
}

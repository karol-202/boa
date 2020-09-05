package pl.karol202.boa.interpreter.handler

import pl.karol202.boa.ast.AssignmentNode
import pl.karol202.boa.interpreter.data.InterpreterContext

object AssignmentHandler : Handler<AssignmentNode, Unit>
{
	override fun InterpreterContext.handle(node: AssignmentNode) =
		handle(AssignmentTargetHandler, node.target) then { target ->
			handle(ExpressionHandler, node.value) then { value ->
				target.assign(this, value) withResult Unit
			}
		}
}

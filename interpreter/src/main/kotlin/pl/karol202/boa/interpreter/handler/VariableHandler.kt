package pl.karol202.boa.interpreter.handler

import pl.karol202.boa.ast.VariableNode
import pl.karol202.boa.interpreter.data.InterpreterContext
import pl.karol202.boa.interpreter.data.Variable
import pl.karol202.boa.interpreter.value.TypeValue
import pl.karol202.boa.interpreter.value.requireToBe
import pl.karol202.boa.type.TypeType

object VariableHandler : Handler<VariableNode, Unit>
{
	override fun InterpreterContext.handle(node: VariableNode) =
		// TODO Infer variable type based on expression type, not value type
		// (maybe introduce ExpressionResult to store expression type along with value?)
		handle(ExpressionHandler, node.value) then { value ->
			val typeValue = node.type?.let { node ->
				requireVariableValue(node.name).requireToBe<TypeValue>(TypeType).value
			} ?: value.type

			withVariable(node.identifier.name, Variable(node.mutability, typeValue, value)) withResult Unit
		}
}

package pl.karol202.boa.interpreter.handler

import pl.karol202.boa.ast.VariableNode
import pl.karol202.boa.interpreter.InterpreterException
import pl.karol202.boa.interpreter.data.InterpreterContext
import pl.karol202.boa.interpreter.data.Variable
import pl.karol202.boa.interpreter.value.TypeValue
import pl.karol202.boa.type.TypeType

object VariableHandler : Handler<VariableNode, Unit>
{
	override fun InterpreterContext.handle(node: VariableNode) =
		handle(ExpressionHandler, node.value) then { value ->
			val typeValue = node.type?.let { node ->
				val typeValue = requireVariableValue(node.name)
				(typeValue as? TypeValue)?.value ?: throw InterpreterException.TypeError(TypeType, typeValue.type)
			} ?: value.type

			withVariable(node.identifier.name, Variable(node.mutability, typeValue, value)) withResult Unit
		}
}

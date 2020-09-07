package pl.karol202.boa.interpreter.data

import pl.karol202.boa.interpreter.value.Value

interface AssignmentTarget
{
	fun assign(context: InterpreterContext, value: Value): InterpreterContext
}

class VariableAssignmentTarget(private val name: String) : AssignmentTarget
{
	override fun assign(context: InterpreterContext, value: Value) = context.withUpdatedVariable(name, value)
}

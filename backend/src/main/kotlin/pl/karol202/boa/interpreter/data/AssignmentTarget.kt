package pl.karol202.boa.interpreter.data

interface AssignmentTarget
{
	fun assign(context: InterpreterContext, value: Any): InterpreterContext
}

class VariableAssignmentTarget(private val variableName: String) : AssignmentTarget
{
	override fun assign(context: InterpreterContext, value: Any) =
		context.withUpdatedVariable(variableName) { it.withValue(value) }
}

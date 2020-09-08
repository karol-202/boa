package pl.karol202.boa.interpreter.data

import pl.karol202.boa.ast.Node
import pl.karol202.boa.interpreter.InterpreterException
import pl.karol202.boa.interpreter.handler.Handler
import pl.karol202.boa.interpreter.util.updated
import pl.karol202.boa.interpreter.value.Value
import pl.karol202.boa.syntax.VariableMutability
import java.io.InputStream
import java.io.OutputStream

class InterpreterContext private constructor(private val io: IO?,
                                             private val variables: Map<String, Variable>)
{
	data class IO(val input: InputStream,
	              val output: OutputStream)

	constructor() : this(null, emptyMap())

	fun <N : Node, R> handle(handler: Handler<N, R>, node: N) = with(handler) { handle(node) }

	infix fun <R> withResult(value: R) = Handler.Result(this, value)

	fun withVariable(name: String, variable: Variable) = when
	{
		name in variables -> throw InterpreterException.VariableAlreadyDeclared(name)
		!variable.value.type.isAssignableTo(variable.type) ->
			throw InterpreterException.TypeError(expected = variable.type, actual = variable.value.type)
		else -> copy(variables = variables + (name to variable))
	}

	fun withUpdatedVariable(name: String, newValue: Value) =
		copy(variables = variables.updated(name) {
			when(it.mutability)
			{
				VariableMutability.IMMUTABLE -> throw InterpreterException.IllegalAssignment(name)
				VariableMutability.MUTABLE ->
					if(newValue.type.isAssignableTo(it.type)) it.withValue(newValue)
					else throw InterpreterException.TypeError(expected = it.type, actual = newValue.type)
			}
		})

	operator fun plus(other: InterpreterContext) =
		InterpreterContext(io = other.io ?: io,
		                   variables = variables + other.variables)

	operator fun minus(other: InterpreterContext) =
		InterpreterContext(io = io.takeIf { it != other.io },
		                   variables = variables.filter { (name, variable) -> variable != other.variables[name] })

	fun requireIO() = io ?: error("No IO found")

	fun withIO(io: IO) = copy(io = io)

	fun requireVariableValue(name: String) = variables[name]?.value ?: throw InterpreterException.VariableNotFound(name)

	private fun copy(io: IO? = null,
	                 variables: Map<String, Variable>? = null) =
		InterpreterContext(io = io ?: this.io, variables = variables ?: this.variables)
}

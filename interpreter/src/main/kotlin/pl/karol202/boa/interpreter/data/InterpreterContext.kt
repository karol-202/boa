package pl.karol202.boa.interpreter.data

import pl.karol202.boa.ast.Node
import pl.karol202.boa.interpreter.InterpreterException
import pl.karol202.boa.interpreter.handler.Handler
import pl.karol202.boa.interpreter.util.updated
import pl.karol202.boa.syntax.VariableType
import java.io.InputStream
import java.io.OutputStream

data class InterpreterContext(val io: IO?,
                              val variables: Map<String, Variable> = emptyMap())
{
	data class IO(val input: InputStream,
	              val output: OutputStream)

	fun <N : Node, R> handle(handler: Handler<N, R>, node: N) = with(handler) { handle(node) }

	infix fun <R> withResult(value: R) = Handler.Result(this, value)

	fun withVariable(name: String, variable: Variable) =
		if(name !in variables) copy(variables = variables + (name to variable))
		else throw InterpreterException.VariableAlreadyDeclared(name)

	fun withUpdatedVariable(name: String, builder: (Variable) -> Variable) =
		copy(variables = variables.updated(name) {
			when(it.type)
			{
				VariableType.IMMUTABLE -> throw InterpreterException.IllegalAssignment(name)
				VariableType.MUTABLE -> builder(it)
			}
		})

	operator fun plus(other: InterpreterContext) =
		InterpreterContext(io = other.io ?: io,
		                   variables = variables + other.variables)

	operator fun minus(other: InterpreterContext) =
		InterpreterContext(io = io.takeIf { it != other.io },
		                   variables = variables.filter { (name, variable) -> variable != other.variables[name] })

	fun requireIO() = io ?: error("No IO found")
}

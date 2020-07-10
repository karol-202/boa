package pl.karol202.boa.interpreter.context

import pl.karol202.boa.ast.Node
import pl.karol202.boa.interpreter.handler.Handler
import java.io.InputStream
import java.io.OutputStream

data class InterpreterContext(val input: InputStream,
                              val output: OutputStream,
                              val variables: Map<String, Any> = emptyMap())
{
	fun <N : Node, R> handle(handler: Handler<N, R>, node: N) = with(handler) { handle(node) }

	infix fun <R> withResult(value: R) = Handler.Result(this, value)

	fun withVariable(name: String, value: Any) = copy(variables = variables + (name to value))
}

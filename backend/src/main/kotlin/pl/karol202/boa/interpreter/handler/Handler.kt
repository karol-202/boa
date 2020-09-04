package pl.karol202.boa.interpreter.handler

import pl.karol202.boa.ast.Node
import pl.karol202.boa.interpreter.data.InterpreterContext

interface Handler<in N : Node, out R>
{
	data class Result<out R>(val context: InterpreterContext,
	                         val value: R)
	{
		infix fun <NR> then(block: InterpreterContext.(R) -> Result<NR>) = context.block(value)
	}

	fun InterpreterContext.handle(node: N): Result<R>
}

infix fun <R> Handler.Result<*>.withResult(value: R) = context withResult value

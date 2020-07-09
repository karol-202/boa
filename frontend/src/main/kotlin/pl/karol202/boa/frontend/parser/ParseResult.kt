package pl.karol202.boa.frontend.parser

import pl.karol202.boa.ast.Node
import pl.karol202.boa.frontend.lexer.Token

sealed class ParseResult<out N : Node>
{
	data class Success<out N : Node>(override val node: N,
	                                 override val remainingTokens: List<Token>,
	                                 override val traceElement: TraceElement) : ParseResult<N>()
	{
		override fun <R> fold(ifSuccess: Success<N>.() -> R, ifFailure: Failure.() -> R) = ifSuccess()

		override fun withTraceElement(traceElement: TraceElement) = copy(traceElement = traceElement)
	}

	data class Failure(override val remainingTokens: List<Token>,
	                   override val traceElement: TraceElement) : ParseResult<Nothing>()
	{
		override val node = null

		override fun <R> fold(ifSuccess: Success<Nothing>.() -> R, ifFailure: Failure.() -> R) = ifFailure()

		override fun withTraceElement(traceElement: TraceElement) = copy(traceElement = traceElement)
	}

	abstract val node: N?
	abstract val remainingTokens: List<Token>
	abstract val traceElement: TraceElement

	abstract fun <R> fold(ifSuccess: Success<N>.() -> R, ifFailure: Failure.() -> R): R

	abstract fun withTraceElement(traceElement: TraceElement): ParseResult<N>
}

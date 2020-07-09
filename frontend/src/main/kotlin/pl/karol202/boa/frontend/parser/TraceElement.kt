package pl.karol202.boa.frontend.parser

import pl.karol202.boa.frontend.lexer.Token
import pl.karol202.boa.frontend.parser.syntax.Syntax

sealed class TraceElement
{
	data class SyntaxElement(val syntax: Syntax<*>,
	                         val child: TraceElement,
	                         val next: TraceElement? = null) : TraceElement()
	{
		override val success get() = child.success
	}

	data class TokenElement(val foundToken: Token?,
	                        override val success: Boolean,
	                        val next: TraceElement? = null) : TraceElement()

	data class EitherElement(val options: List<TraceElement>) : TraceElement()
	{
		override val success get() = options.any { it.success }
	}

	data class TerminalElement(override val success: Boolean) : TraceElement()

	abstract val success: Boolean
}

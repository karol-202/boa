package pl.karol202.boa.frontend.parser

import pl.karol202.boa.ast.Node
import pl.karol202.boa.frontend.lexer.Token

sealed class ParseResult<out N : Node>
{
	data class Success<out N : Node>(override val node: N,
	                                 override val remainingTokens: List<Token>,
	                                 override val issues: List<ParserIssue> = emptyList()) : ParseResult<N>()
	{
		override fun <R : Node> flatMap(transform: Success<N>.() -> ParseResult<R>) = transform()
	}

	data class Failure(override val remainingTokens: List<Token>,
	                   override val issues: List<ParserIssue> = emptyList()) : ParseResult<Nothing>()
	{
		override val node = null

		override fun <R : Node> flatMap(transform: Success<Nothing>.() -> ParseResult<R>) = this
	}

	abstract val node: N?
	abstract val remainingTokens: List<Token>
	abstract val issues: List<ParserIssue>

	abstract fun <R : Node> flatMap(transform: Success<N>.() -> ParseResult<R>): ParseResult<R>
}

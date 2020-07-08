package pl.karol202.boa.frontend.parser

import pl.karol202.boa.ast.Node
import pl.karol202.boa.frontend.lexer.Token

sealed class ParseResult<out N : Node>
{
	data class Success<N : Node>(override val node: N,
	                             override val remainingTokens: List<Token>,
	                             override val issues: List<ParserIssue>) : ParseResult<N>()

	data class Failure(override val remainingTokens: List<Token>,
	                   override val issues: List<ParserIssue>) : ParseResult<Nothing>()
	{
		override val node = null
	}

	abstract val node: N?
	abstract val remainingTokens: List<Token>
	abstract val issues: List<ParserIssue>
}

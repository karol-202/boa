package pl.karol202.boa.frontend.parser.structure

import pl.karol202.boa.ast.Node
import pl.karol202.boa.frontend.lexer.Token
import pl.karol202.boa.frontend.parser.ParseResult
import pl.karol202.boa.frontend.parser.ParserIssue

abstract class AbstractStructure<N : Node> : Structure<N>
{
	class Builder(tokens: List<Token>)
	{
		var tokens = tokens
			private set
		var issues = emptyList<ParserIssue>()
			private set

		val nextToken get() = tokens.firstOrNull()

		fun <N : Node> parseStructure(structure: Structure<N>, required: Boolean = true): N?
		{
			val result = structure.parse(tokens)
			if(result is ParseResult.Success) tokens = result.remainingTokens
			if(result is ParseResult.Success || required) issues += result.issues
			return result.node
		}

		@JvmName("parseTokenTyped")
		inline fun <reified T : Token> parseToken(crossinline predicate: (T) -> Boolean = { true }) =
			parseToken { it is T && predicate(it) } as T?

		fun parseToken(predicate: (Token) -> Boolean = { true }) =
			tokens.firstOrNull()?.takeIf(predicate)?.also { tokens = tokens.drop(1) }

		fun fail(issue: ParserIssue) = null.also { issues += issue }

		fun skipUntil(predicate: (Token) -> Boolean)
		{
			tokens = tokens.dropWhile { !predicate(it) }
		}
	}

	final override fun parse(tokens: List<Token>): ParseResult<N>
	{
		val builder = Builder(tokens)
		val node = builder.parse()
		return if(node != null) ParseResult.Success(node, builder.tokens, builder.issues)
		else ParseResult.Failure(builder.tokens, builder.issues)
	}

	abstract fun Builder.parse(): N?
}

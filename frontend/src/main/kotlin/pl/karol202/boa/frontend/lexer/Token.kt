package pl.karol202.boa.frontend.lexer

import pl.karol202.boa.syntax.KeywordType

sealed class Token
{
	data class Keyword(val type: KeywordType) : Token()

	data class Identifier(val name: String) : Token()

	data class Special(val value: String) : Token()

	sealed class Literal : Token()
	{
		data class String(val value: kotlin.String) : Literal()

		data class Integer(val value: Int) : Literal()

		data class Real(val value: Double) : Literal()
	}

	sealed class Comment : Token()
	{
		data class SingleLine(val value: String) : Comment()

		data class MultiLine(val value: String) : Comment()
	}

	sealed class Block : Token()
	{
		object Open : Block()

		object Close : Block()
	}

	sealed class Parenthesis : Token()
	{
		object Open : Parenthesis()

		object Close : Parenthesis()
	}

	object Dot : Token()

	object Comma : Token()

	object Newline : Token()

	data class Invalid(val value: String) : Token()
}

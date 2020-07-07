package pl.karol202.boa.frontend.lexer

import pl.karol202.boa.syntax.KeywordType

sealed class Token
{
	data class Keyword(val type: KeywordType) : Token()

	data class Identifier(val name: String) : Token()

	data class Special(val value: String) : Token()

	data class StringLiteral(val value: String) : Token()

	data class IntegerLiteral(val value: Int) : Token()

	data class RealLiteral(val value: Double) : Token()

	data class SingleLineComment(val value: String) : Token()

	data class MultiLineComment(val value: String) : Token()

	object BlockOpen : Token()

	object BlockClose : Token()

	object ParenthesisOpen : Token()

	object ParenthesisClose : Token()

	object Comma : Token()

	object Newline : Token()

	data class Invalid(val value: String) : Token()
}

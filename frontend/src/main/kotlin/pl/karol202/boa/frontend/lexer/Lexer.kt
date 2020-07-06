package pl.karol202.boa.frontend.lexer

import pl.karol202.boa.Phase
import pl.karol202.boa.syntax.KeywordType

class Lexer : Phase<String, List<Token>>
{
	private data class State(val tokens: List<Token> = emptyList(),
	                         val pendingToken: PendingToken? = null)
	{
		fun withNewToken(token: Token) = copy(tokens = tokens + token)

		fun withPendingToken(pendingToken: PendingToken?) = copy(pendingToken = pendingToken)
	}

	private data class PendingToken(val value: String,
	                                val type: Type)
	{
		enum class Type
		{
			TEXTUAL,
			SPECIAL,
			STRING_LITERAL,
			NUMBER_LITERAL
		}
	}

	override fun process(input: String) = input.fold(State()) { state, char -> state.processChar(char) }.tokens

	private fun State.processChar(char: Char) = when(char)
	{
		CHAR_SPACE -> finishPendingTokenIfAny()
		else -> throw LexingException.invalidCharacter(char)
	}

	private fun State.finishPendingTokenIfAny() = pendingToken?.let { pending ->
		val token = pending.interpretPendingToken()
		withNewToken(token).withPendingToken(null)
	} ?: this

	private fun PendingToken.interpretPendingToken() = when(type)
	{
		PendingToken.Type.TEXTUAL -> KeywordType.findBySymbol(value)?.let { Token.Keyword(it) } ?: Token.Identifier(value)
		PendingToken.Type.SPECIAL -> Token.Special(value)
		PendingToken.Type.STRING_LITERAL -> Token.StringLiteral(value)
		PendingToken.Type.NUMBER_LITERAL ->
			value.toIntOrNull()?.let { Token.IntegerLiteral(it) } ?:
			value.toDoubleOrNull()?.let { Token.RealLiteral(it) } ?:
			throw LexingException.invalidNumberLiteral(value)
	}
}

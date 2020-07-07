package pl.karol202.boa.frontend.lexer

import pl.karol202.boa.Phase
import pl.karol202.boa.frontend.LexerActions

class Lexer(private val newlineChar: Char) : Phase<String, List<Token>>
{
	private val rules = buildRules {
		+ LexerRule.onPendingToken(
			matchToken = { isStringLiteral },
			matchChar = { it == '\"' },
			action = LexerActions.finish { _, token -> Token.StringLiteral(token.value.drop(1)) }
		)
		+ LexerRule.onPendingToken(
			matchToken = { isStringLiteral },
			action = LexerActions.append
		)
		+ LexerRule.onPendingToken(
			matchToken = { isMultiLineComment && endsWithAsterisk },
			matchChar = { it == '/' },
			action = LexerActions.finish { _, token -> Token.MultiLineComment(token.value.drop(2).dropLast(1)) }
		)
		+ LexerRule.onPendingToken(
			matchToken = { isComment },
			action = LexerActions.append
		)
		+ LexerRule.onPendingToken(
			matchToken = { isCommentStartMarker },
			matchChar = { it == '/' || it == '*' },
			action = LexerActions.append
		)
	}

	override fun process(input: String) = input.fold(LexerState(), this::processChar).tokens

	private fun processChar(state: LexerState, char: Char) =
		rules.first { it.matcher(state, char) }.action(state, char)

	/*private fun LexerState.processChar(char: Char): LexerState
	{
		if(pendingToken != null && pendingToken.isCurrentlyEscapedStringLiteral)
			return withPendingToken(pendingToken.charAppended(char))

		if(char == '\"') processQuotationMark(char)

		when(char)
		{
			'\"' ->
			Chars.SPACE, Chars.TAB -> finishPendingTokenIfAny()
			newlineChar -> finishPendingTokenIfAny().withNewToken(Token.Newline).withNextLine()
			'{' -> finishPendingTokenIfAny().withNewToken(Token.BlockOpen)
			'}' -> finishPendingTokenIfAny().withNewToken(Token.BlockClose)
			'(' -> finishPendingTokenIfAny().withNewToken(Token.ParenthesisOpen)
			')' -> finishPendingTokenIfAny().withNewToken(Token.ParenthesisClose)
			',' -> finishPendingTokenIfAny().withNewToken(Token.Comma)
			in 'A'..'Z', in 'a'..'z' -> appendToPendingTokenOrNull(char) ?: beginPendingToken(char)
			in '0'..'9' -> appendToPendingTokenOrNull(char) ?: beginPendingToken(char)
			else -> throw LexingIssue.invalidCharacter(char)
		}
	}*/

	/*private fun LexerState.processQuotationMark(char: Char) = when
	{
		pendingToken == null -> beginPendingToken(char)
		pendingToken.isStringLiteral -> withPendingToken(pendingToken.charAppended(char)).finishPendingTokenIfAny()
		else -> throw LexingIssue.unexpectedCharacter(char)
	}

	private fun LexerState.beginPendingToken(char: Char) = withPendingToken(PendingToken(char.toString()))

	private fun LexerState.appendToPendingTokenOrNull(char: Char) = pendingToken?.let { pending ->
		withPendingToken(pending.charAppended(char))
	}

	private fun LexerState.finishPendingTokenIfAny() = pendingToken?.let { pending ->
		val token = pending.interpretPendingToken()
		withNewToken(token).withPendingToken(null)
	} ?: this

	private fun LexerState.PendingToken.interpretPendingToken() = when(type)
	{
		PendingToken.Type.TEXTUAL -> KeywordType.findBySymbol(value)?.let { Token.Keyword(it) } ?: Token.Identifier(value)
		PendingToken.Type.SPECIAL -> Token.Special(value)
		PendingToken.Type.STRING_LITERAL -> Token.StringLiteral(value)
		PendingToken.Type.NUMBER_LITERAL ->
			value.toIntOrNull()?.let { Token.IntegerLiteral(it) } ?:
			value.toDoubleOrNull()?.let { Token.RealLiteral(it) } ?:
			throw LexingIssue.invalidNumberLiteral(value)
	}*/
}

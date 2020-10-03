package pl.karol202.boa.frontend.lexer

import pl.karol202.boa.Phase
import pl.karol202.boa.syntax.Chars
import pl.karol202.boa.syntax.KeywordType

object Lexer : Phase<String, List<Token>>
{
	private val rules = buildRules {
		+ LexerRule.onPendingToken(
			matchToken = { isStringLiteral || isMultiLineComment },
			matchChar = { it == Chars.LINE_SEPARATOR },
			action = append
					then moveToNextLine
		)
		+ LexerRule.onPendingToken(
			matchToken = { isStringLiteral },
			matchChar = { it == '\"' },
			action = finish { token -> Token.Literal.String(token.value.drop(1)) }
		)
		+ LexerRule.onPendingToken(
			matchToken = { isStringLiteral },
			action = append
		)
		+ LexerRule.onPendingToken(
			matchToken = { isMultiLineComment && value.endsWith('*') },
			matchChar = { it == '/' },
			action = finish { token -> Token.Comment.MultiLine(token.value.drop(2).dropLast(1)) }
		)
		+ LexerRule.onPendingToken(
			matchToken = { isSingleLineComment },
			matchChar = { it == Chars.LINE_SEPARATOR },
			action = finish { token -> Token.Comment.SingleLine(token.value.drop(2)) }
					then add { Token.Newline }
					then moveToNextLine
		)
		+ LexerRule.onPendingToken(
			matchToken = { isSingleLineComment },
			matchChar = { it == Chars.EOF },
			action = finish { token -> Token.Comment.SingleLine(token.value.drop(2)) }
		)
		+ LexerRule.onPendingToken(
			matchToken = { isComment },
			action = append
		)
		+ LexerRule.onPendingToken(
			matchToken = { isCommentStartMarker },
			matchChar = { it == '/' || it == '*' },
			action = append
		)
		+ LexerRule.simple(
			matchChar = { it == '/' },
			action = ifTokenPending { finishWithPossibleIssue { token -> interpretPendingToken(token) } }
					then begin
		)
		+ LexerRule.simple(
			matchChar = { it == Chars.SPACE || it == Chars.TAB || it == Chars.EOF },
			action = ifTokenPending { finishWithPossibleIssue { token -> interpretPendingToken(token) } }
		)
		+ LexerRule.simple(
			matchChar = { it == Chars.LINE_SEPARATOR },
			action = ifTokenPending { finishWithPossibleIssue { token -> interpretPendingToken(token) } }
					then add { Token.Newline }
					then moveToNextLine
		)
		+ LexerRule.simple(
			matchChar = { it == '{' },
			action = ifTokenPending { finishWithPossibleIssue { token -> interpretPendingToken(token) } }
					then add { Token.Block.Open }
		)
		+ LexerRule.simple(
			matchChar = { it == '}' },
			action = ifTokenPending { finishWithPossibleIssue { token -> interpretPendingToken(token) } }
					then add { Token.Block.Close }
		)
		+ LexerRule.simple(
			matchChar = { it == '(' },
			action = ifTokenPending { finishWithPossibleIssue { token -> interpretPendingToken(token) } }
					then add { Token.Parenthesis.Open }
		)
		+ LexerRule.simple(
			matchChar = { it == ')' },
			action = ifTokenPending { finishWithPossibleIssue { token -> interpretPendingToken(token) } }
					then add { Token.Parenthesis.Close }
		)
		+ LexerRule.simple(
			matchChar = { it == ',' },
			action = ifTokenPending { finishWithPossibleIssue { token -> interpretPendingToken(token) } }
					then add { Token.Comma }
		)
		+ LexerRule.onPendingToken(
			matchToken = { isNumberLiteral },
			matchChar = { it == '.' },
			action = append
		)
		+ LexerRule.simple(
			matchChar = { it == '.' },
			action = ifTokenPending { finishWithPossibleIssue { token -> interpretPendingToken(token) } }
					then add { Token.Dot }
		)
		+ LexerRule.simple(
			matchChar = { it == '"' },
			action = ifTokenPending { finishWithPossibleIssue { token -> interpretPendingToken(token) } }
					then begin
		)
		+ LexerRule.onPendingToken(
			matchToken = { isAlphanumericOnly || isNumberLiteral },
			matchChar = { it.isLetterOrDigit() },
			action = append
		)
		+ LexerRule.simple(
			matchChar = { it.isLetterOrDigit() },
			action = ifTokenPending { finishWithPossibleIssue { token -> interpretPendingToken(token) } }
					then begin
		)
		+ LexerRule.onPendingToken(
			matchToken = { isSpecialOnly },
			matchChar = { it in Chars.SPECIAL },
			action = append
		)
		+ LexerRule.simple(
			matchChar = { it in Chars.SPECIAL },
			action = ifTokenPending { finishWithPossibleIssue { token -> interpretPendingToken(token) } }
					then begin
		)
		+ LexerRule.simple(
			action = ifTokenPending { finishWithPossibleIssue { token -> interpretPendingToken(token) } }
					then add { char -> Token.Invalid(char.toString()) }
					then issue { char -> LexerIssue.IllegalCharacter(char) }
		)
	}

	override fun process(input: String): Phase.Result.Success<List<Token>>
	{
		val state = (input + Chars.EOF).fold(LexerState(), this::processChar)
		return Phase.Result.Success(state.tokens, state.issues)
	}

	private fun processChar(state: LexerState, char: Char) =
		rules.first { it.matcher(state, char) }.action(state, char)

	private fun interpretPendingToken(token: LexerState.PendingToken): Pair<Token, LexerIssue?>
	{
		fun invalid() = Token.Invalid(token.value)
		fun Token.withNoIssue() = this to null
		fun Token.withIssue(issue: LexerIssue) = this to issue

		return when
		{
			token.isStringLiteral ->
				if(token.value.endsWith('\"')) Token.Literal.String(token.value).withNoIssue()
				else invalid().withIssue(LexerIssue.InvalidStringLiteral(token.value))
			token.isNumberLiteral ->
				token.value.toIntOrNull()?.let { Token.Literal.Integer(it).withNoIssue() } ?:
				token.value.toDoubleOrNull()?.let { Token.Literal.Real(it).withNoIssue() } ?:
				invalid().withIssue(LexerIssue.InvalidNumberLiteral(token.value))
			token.isAlphanumericOnly ->
				KeywordType.findBySymbol(token.value)?.let { Token.Keyword(it).withNoIssue() } ?:
				Token.Identifier(token.value).withNoIssue()
			token.isSpecialOnly ->
				Token.Special(token.value).withNoIssue()
			else ->
				invalid().withIssue(LexerIssue.InvalidToken(token.value))
		}
	}
}

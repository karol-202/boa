package pl.karol202.boa.frontend.lexer

import pl.karol202.boa.Phase
import pl.karol202.boa.syntax.KeywordType

class Lexer(private val lineSeparator: Char) : Phase<String, List<Token>>
{
	private val rules = buildRules {
		+ LexerRule.onPendingToken(
			matchToken = { isStringLiteral || isMultiLineComment },
			matchChar = { it == lineSeparator },
			action = append
					then moveToNextLine
		)
		+ LexerRule.onPendingToken(
			matchToken = { isStringLiteral },
			matchChar = { it == '\"' },
			action = finish { token -> Token.StringLiteral(token.value.drop(1)) }
		)
		+ LexerRule.onPendingToken(
			matchToken = { isStringLiteral },
			action = append
		)
		+ LexerRule.onPendingToken(
			matchToken = { isMultiLineComment && value.endsWith('*') },
			matchChar = { it == '/' },
			action = finish { token -> Token.MultiLineComment(token.value.drop(2).dropLast(1)) }
		)
		+ LexerRule.onPendingToken(
			matchToken = { isSingleLineComment },
			matchChar = { it == lineSeparator },
			action = finish { token -> Token.SingleLineComment(token.value.drop(2)) }
					then add { Token.Newline }
					then moveToNextLine
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
		+ LexerRule.onPendingToken(
			matchToken = { isCommentStartMarker },
			action = appendAndFinishInvalid
					then issue { char -> LexerIssue.UnexpectedCharacter(char) }
		)
		+ LexerRule.onPendingToken(
			matchToken = { true },
			matchChar = { it == '/' },
			action = finishWithPossibleIssue { token -> interpretPendingToken(token) }
					then begin
		)
		+ LexerRule.onPendingToken(
			matchChar = { it == Chars.SPACE || it == Chars.TAB },
			action = finishWithPossibleIssue { token -> interpretPendingToken(token) }
		)
		+ LexerRule.simple(
			matchChar = { it == lineSeparator },
			action = ifTokenPending { finishWithPossibleIssue { token -> interpretPendingToken(token) } }
					then add { Token.Newline }
					then moveToNextLine
		)
		+ LexerRule.simple(
			matchChar = { it == '{' },
			action = ifTokenPending { finishWithPossibleIssue { token -> interpretPendingToken(token) } }
					then add { Token.BlockOpen }
		)
		+ LexerRule.simple(
			matchChar = { it == '}' },
			action = ifTokenPending { finishWithPossibleIssue { token -> interpretPendingToken(token) } }
					then add { Token.BlockClose }
		)
		+ LexerRule.simple(
			matchChar = { it == '(' },
			action = ifTokenPending { finishWithPossibleIssue { token -> interpretPendingToken(token) } }
					then add { Token.ParenthesisOpen }
		)
		+ LexerRule.simple(
			matchChar = { it == ')' },
			action = ifTokenPending { finishWithPossibleIssue { token -> interpretPendingToken(token) } }
					then add { Token.ParenthesisClose }
		)
		+ LexerRule.simple(
			matchChar = { it == ',' },
			action = ifTokenPending { finishWithPossibleIssue { token -> interpretPendingToken(token) } }
					then add { Token.Comma }
		)
		+ LexerRule.simple(
			matchChar = { it.isLetterOrDigit() || it in Chars.SPECIAL },
			action = ifTokenPending(then = { append }, orElse = { begin })
		)
		+ LexerRule.simple(
			action = issue { char -> LexerIssue.IllegalCharacter(char) }
		)
	}

	override fun process(input: String): Phase.Result<List<Token>>
	{
		val state = input.fold(LexerState(), this::processChar)
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
				if(token.value.endsWith('\"')) Token.StringLiteral(token.value).withNoIssue()
				else invalid().withIssue(LexerIssue.InvalidStringLiteral(token.value))
			token.isNumberLiteral ->
				token.value.toIntOrNull()?.let { Token.IntegerLiteral(it).withNoIssue() } ?:
				token.value.toDoubleOrNull()?.let { Token.RealLiteral(it).withNoIssue() } ?:
				invalid().withIssue(LexerIssue.InvalidNumberLiteral(token.value))
			token.value.all { it.isLetterOrDigit() } ->
				KeywordType.findBySymbol(token.value)?.let { Token.Keyword(it).withNoIssue() } ?:
				Token.Identifier(token.value).withNoIssue()
			token.value.all { it in Chars.SPECIAL } ->
				Token.Special(token.value).withNoIssue()
			else ->
				invalid().withIssue(LexerIssue.InvalidToken(token.value))
		}
	}
}

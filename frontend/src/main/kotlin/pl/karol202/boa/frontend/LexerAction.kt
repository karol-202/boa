package pl.karol202.boa.frontend

import pl.karol202.boa.frontend.lexer.LexerIssue
import pl.karol202.boa.frontend.lexer.LexerState
import pl.karol202.boa.frontend.lexer.Token

private typealias SimpleLexerAction = LexerState.(Char) -> LexerState
private typealias PendingTokenLexerAction = LexerState.(Char, LexerState.PendingToken) -> LexerState

interface LexerActionScope
{
	val begin: SimpleLexerAction
		get() = { char -> withPendingToken(LexerState.PendingToken(char)) }

	val append: PendingTokenLexerAction
		get() = { char, token -> withPendingToken(token.charAppended(char)) }

	val appendAndFinishInvalid: PendingTokenLexerAction
		get() = appendAndFinish { token -> Token.Invalid(token.value) }

	val moveToNextLine: SimpleLexerAction
		get() = { _ -> withNextLine() }

	fun appendAndFinish(tokenProvider: (LexerState.PendingToken) -> Token): PendingTokenLexerAction = { char, token ->
		val appendedToken = token.charAppended(char)
		withPendingToken(appendedToken).withNewToken(tokenProvider(appendedToken)).withPendingToken(null)
	}

	fun finish(tokenProvider: (LexerState.PendingToken) -> Token): PendingTokenLexerAction = { _, token ->
		withNewToken(tokenProvider(token)).withPendingToken(null)
	}

	fun finishWithPossibleIssue(tokenProvider: (LexerState.PendingToken) -> Pair<Token, LexerIssue?>): PendingTokenLexerAction = { _, token ->
		val (finishedToken, issue) = tokenProvider(token)
		val newState = withNewToken(finishedToken).withPendingToken(null)
		if(issue != null) newState.withIssue(issue) else newState
	}

	fun add(tokenProvider: (Char) -> Token): SimpleLexerAction = { char ->
		withNewToken(tokenProvider(char))
	}

	fun issue(issueProvider: LexerState.(Char) -> LexerIssue): SimpleLexerAction = { char ->
		withIssue(issueProvider(this, char))
	}

	fun ifTokenPending(then: () -> PendingTokenLexerAction) = ifTokenPending(then = then, orElse = { { this } })

	fun ifTokenPending(then: () -> PendingTokenLexerAction,
	                   orElse: () -> SimpleLexerAction): SimpleLexerAction = { char ->
		if(pendingToken != null) then()(char, pendingToken)
		else orElse()(char)
	}

	infix fun SimpleLexerAction.then(action: SimpleLexerAction): SimpleLexerAction = { char ->
		this.this@then(char).action(char)
	}

	infix fun PendingTokenLexerAction.then(action: SimpleLexerAction): PendingTokenLexerAction = { char, token ->
		this.this@then(char, token).action(char)
	}
}

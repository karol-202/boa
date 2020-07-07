package pl.karol202.boa.frontend

import pl.karol202.boa.frontend.lexer.LexerState
import pl.karol202.boa.frontend.lexer.Token

private typealias LexerAction = LexerState.(Char, LexerState.PendingToken) -> LexerState

object LexerActions
{
	val append: LexerAction = { char, token -> withPendingToken(token.charAppended(char)) }

	fun finish(tokenProvider: (Char, LexerState.PendingToken) -> Token): LexerAction = { char, token ->
		withNewToken(tokenProvider(char, token)).withPendingToken(null)
	}
}

package pl.karol202.boa.frontend.lexer

data class LexerRule(val matcher: (LexerState, Char) -> Boolean,
                     val action: (LexerState, Char) -> LexerState)
{
    companion object
    {
        fun simple(matchState: LexerState.() -> Boolean = { true },
                   matchChar: (Char) -> Boolean = { true },
                   action: LexerState.(Char) -> LexerState) =
            LexerRule(matcher = { state, char -> state.matchState() && matchChar(char) },
                      action = { state, char -> state.action(char) })

        fun onPendingToken(matchToken: LexerState.PendingToken.() -> Boolean = { true },
                           matchChar: (Char) -> Boolean = { true },
                           action: LexerState.(Char, LexerState.PendingToken) -> LexerState) =
            LexerRule(
                matcher = { state, char ->
                    state.pendingToken != null && state.pendingToken.matchToken() && matchChar(char)
                },
                action = { state, char ->
                    state.action(char, state.pendingToken ?: throw IllegalStateException("No pending token"))
                })
    }
}

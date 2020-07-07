package pl.karol202.boa.frontend.lexer

data class LexerState(val tokens: List<Token> = emptyList(),
                      val pendingToken: PendingToken? = null,
                      val lineNumber: Int = 1)
{
	data class PendingToken(val value: String)
	{
		val isStringLiteral get() = value.startsWith('\"')
		val isNumberLiteral get() = value.first().isDigit()
		val isSingleLineComment get() = value.startsWith("//")
		val isMultiLineComment get() = value.startsWith("/*")
		val isComment get() = isSingleLineComment || isMultiLineComment
		val isCommentStartMarker get() = value.singleOrNull()?.equals('/') ?: false
		val endsWithAsterisk get() = value.endsWith('*')

		fun charAppended(char: Char) = copy(value = value + char)
	}

	fun withNewToken(token: Token) = copy(tokens = tokens + token)

	fun withPendingToken(pendingToken: PendingToken?) = copy(pendingToken = pendingToken)

	fun withNextLine() = copy(lineNumber = lineNumber + 1)
}

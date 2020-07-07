package pl.karol202.boa.frontend.lexer

import pl.karol202.boa.Issue

data class LexerState(val tokens: List<Token> = emptyList(),
                      val pendingToken: PendingToken? = null,
                      val lineNumber: Int = 1,
                      val issues: List<Issue> = emptyList())
{
	data class PendingToken(val value: String)
	{
		val isStringLiteral get() = value.startsWith('\"')
		val isNumberLiteral get() = value.first().isDigit()
		val isSingleLineComment get() = value.startsWith("//")
		val isMultiLineComment get() = value.startsWith("/*")
		val isComment get() = isSingleLineComment || isMultiLineComment
		val isCommentStartMarker get() = value.singleOrNull()?.equals('/') ?: false

		constructor(firstChar: Char) : this(firstChar.toString())

		fun charAppended(char: Char) = copy(value = value + char)
	}

	fun withNewToken(token: Token) = copy(tokens = tokens + token)

	fun withPendingToken(pendingToken: PendingToken?) = copy(pendingToken = pendingToken)

	fun withNextLine() = copy(lineNumber = lineNumber + 1)

	fun withIssue(issue: Issue) = copy(issues = issues + issue)
}

package pl.karol202.boa.frontend.lexer

import pl.karol202.boa.Issue

sealed class LexerIssue(override val level: Issue.Level,
                        override val message: String) : Issue
{
	class IllegalCharacter(char: Char) : LexerIssue(Issue.Level.ERROR,
	                                                "Invalid character: $char")

	class UnexpectedCharacter(char: Char) : LexerIssue(Issue.Level.ERROR,
	                                                   "Unexpected character: $char")

	class InvalidToken(token: String) : LexerIssue(Issue.Level.ERROR,
	                                               "Invalid token: $token")

	class InvalidStringLiteral(token: String) : LexerIssue(Issue.Level.ERROR,
	                                                       "Invalid string literal: $token")

	class InvalidNumberLiteral(token: String) : LexerIssue(Issue.Level.ERROR,
	                                                       "Invalid number literal: $token")
}

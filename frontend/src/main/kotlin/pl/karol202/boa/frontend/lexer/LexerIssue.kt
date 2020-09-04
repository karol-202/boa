package pl.karol202.boa.frontend.lexer

import pl.karol202.boa.Issue

sealed class LexerIssue(level: Level,
                        message: String) : Issue(level, message)
{
	class IllegalCharacter(char: Char) : LexerIssue(Level.ERROR,
	                                                "Invalid character: $char")

	class UnexpectedCharacter(char: Char) : LexerIssue(Level.ERROR,
	                                                   "Unexpected character: $char")

	class InvalidToken(token: String) : LexerIssue(Level.ERROR,
	                                               "Invalid token: $token")

	class InvalidStringLiteral(token: String) : LexerIssue(Level.ERROR,
	                                                       "Invalid string literal: $token")

	class InvalidNumberLiteral(token: String) : LexerIssue(Level.ERROR,
	                                                       "Invalid number literal: $token")
}

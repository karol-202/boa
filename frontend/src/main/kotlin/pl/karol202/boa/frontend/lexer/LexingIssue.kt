package pl.karol202.boa.frontend.lexer

import pl.karol202.boa.Issue

sealed class LexingIssue(override val level: Issue.Level,
                         override val message: String) : Issue
{
	class IllegalCharacter(char: Char) : LexingIssue(Issue.Level.ERROR,
	                                                 "Invalid character: $char")

	class UnexpectedCharacter(char: Char) : LexingIssue(Issue.Level.ERROR,
	                                                    "Unexpected character: $char")

	class InvalidNumberLiteral(token: String) : LexingIssue(Issue.Level.ERROR,
	                                                        "Invalid number literal: $token")
}

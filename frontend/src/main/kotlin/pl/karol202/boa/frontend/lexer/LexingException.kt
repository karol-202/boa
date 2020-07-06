package pl.karol202.boa.frontend.lexer

import pl.karol202.boa.CompilationException

class LexingException(message: String) : CompilationException(message)
{
	companion object
	{
		fun invalidCharacter(char: Char) = LexingException("Invalid character: $char")

		fun invalidNumberLiteral(token: String) = LexingException("Invalid number literal: $token")
	}
}

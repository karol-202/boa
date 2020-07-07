package pl.karol202.boa.frontend.lexer

object Chars
{
	const val SPACE = '\u0020'
	const val TAB = '\t'
	const val EOF = '\u0000'

	val SPECIAL = listOf('+', '-', '*', '/', '%', '=', '&', '|', '!', '<', '>')
}

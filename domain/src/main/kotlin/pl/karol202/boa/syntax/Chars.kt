package pl.karol202.boa.syntax

object Chars
{
	const val LINE_SEPARATOR = '\n'
	const val SPACE = '\u0020'
	const val TAB = '\t'
	const val EOF = '\u0000'

	val SPECIAL = listOf('+', '-', '*', '/', '%', '=', '&', '|', '!', '<', '>')
}

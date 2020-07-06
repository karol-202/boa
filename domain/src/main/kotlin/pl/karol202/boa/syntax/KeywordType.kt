package pl.karol202.boa.syntax

enum class KeywordType(val symbol: String)
{
	LET("let"),
	VAR("var");

	companion object
	{
		fun findBySymbol(symbol: String) = values().firstOrNull { it.symbol == symbol }
	}
}

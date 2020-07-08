package pl.karol202.boa.syntax

enum class KeywordType(val symbol: String)
{
	LET("let"),
	VAR("var"),
	RETURN("return");

	companion object
	{
		val variable = listOf(LET, VAR)

		fun findBySymbol(symbol: String) = values().firstOrNull { it.symbol == symbol }
	}
}

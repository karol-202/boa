package pl.karol202.boa.syntax

enum class KeywordType(val symbol: String)
{
	IMPORT("import"),
	LET("let"),
	VAR("var"),
	IF("if"),
	ELSE("else");

	companion object
	{
		val variable = listOf(LET, VAR)

		fun findBySymbol(symbol: String) = values().firstOrNull { it.symbol == symbol }
	}
}

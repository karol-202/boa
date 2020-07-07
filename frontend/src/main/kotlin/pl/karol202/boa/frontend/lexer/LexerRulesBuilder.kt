package pl.karol202.boa.frontend.lexer

interface LexerRulesBuilder
{
	operator fun LexerRule.unaryPlus()
}

class LexerRulesBuilderImpl : LexerRulesBuilder
{
	val rules = mutableListOf<LexerRule>()

	override operator fun LexerRule.unaryPlus()
	{
		rules += this
	}
}

fun buildRules(builder: LexerRulesBuilder.() -> Unit) = LexerRulesBuilderImpl().apply(builder).rules

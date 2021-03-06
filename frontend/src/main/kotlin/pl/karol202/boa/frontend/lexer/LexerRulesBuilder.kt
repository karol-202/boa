package pl.karol202.boa.frontend.lexer

interface LexerRulesBuilder : LexerActionScope
{
	operator fun LexerRule.unaryPlus()
}

class LexerRulesBuilderImpl : LexerRulesBuilder, LexerActionScope
{
	val rules = mutableListOf<LexerRule>()

	override operator fun LexerRule.unaryPlus()
	{
		rules += this
	}
}

fun buildRules(builder: LexerRulesBuilder.() -> Unit) = LexerRulesBuilderImpl().apply(builder).rules

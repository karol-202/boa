package pl.karol202.boa.frontend.parser.structure

import pl.karol202.boa.ast.VariableNode
import pl.karol202.boa.frontend.lexer.Token
import pl.karol202.boa.frontend.parser.ParserIssue
import pl.karol202.boa.syntax.KeywordType
import pl.karol202.boa.syntax.VariableType

object VariableStructure : AbstractStructure<VariableNode>()
{
	override fun Builder.parse(): VariableNode?
	{
		val keyword = parseToken<Token.Keyword> { it.type in KeywordType.variable }
			?: return fail(ParserIssue.VariableKeywordRequired)
		val identifier = parseStructure(IdentifierStructure) ?: return null
		parseToken<Token.Special> { it.value == "=" } ?: return fail(ParserIssue.VariableAssignmentOperatorRequired)
		val value = parseStructure(ExpressionStructure) ?: return null

		val variableType = when(keyword.type)
		{
			KeywordType.LET -> VariableType.IMMUTABLE
			KeywordType.VAR -> VariableType.MUTABLE
			else -> throw IllegalStateException("Invalid variable keyword: ${keyword.type}")
		}
		return VariableNode(variableType, identifier, value)
	}
}

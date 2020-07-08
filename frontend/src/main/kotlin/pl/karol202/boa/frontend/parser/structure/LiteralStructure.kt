package pl.karol202.boa.frontend.parser.structure

import pl.karol202.boa.ast.LiteralNode
import pl.karol202.boa.frontend.lexer.Token
import pl.karol202.boa.frontend.parser.ParserIssue

object LiteralStructure : AbstractStructure<LiteralNode>()
{
	override fun Builder.parse(): LiteralNode?
	{
		val literal = parseToken<Token.Literal>() ?: return fail(ParserIssue.LiteralRequired)
		return when(literal)
		{
			is Token.Literal.String -> LiteralNode.StringLiteral(literal.value)
			is Token.Literal.Integer -> LiteralNode.IntegerLiteral(literal.value)
			is Token.Literal.Real -> LiteralNode.RealLiteral(literal.value)
		}
	}
}

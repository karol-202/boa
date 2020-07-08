package pl.karol202.boa.frontend.parser.syntax

import pl.karol202.boa.ast.LiteralNode
import pl.karol202.boa.frontend.lexer.Token

object LiteralSyntax : AbstractSyntax<LiteralNode>()
{
	override fun SyntaxScope.parse() =
		token<Token.Literal>() then { literal ->
			when(literal)
			{
				is Token.Literal.String -> LiteralNode.StringLiteral(literal.value)
				is Token.Literal.Integer -> LiteralNode.IntegerLiteral(literal.value)
				is Token.Literal.Real -> LiteralNode.RealLiteral(literal.value)
			}.finish()
		}
}

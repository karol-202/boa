package pl.karol202.boa.frontend.parser.syntax

import pl.karol202.boa.ast.LiteralNode
import pl.karol202.boa.frontend.lexer.Token

object IntegerLiteralSyntax : AbstractSyntax<LiteralNode.IntegerLiteral>()
{
	override fun SyntaxScope.syntax() =
		token<Token.Literal.Integer>() then { literal ->
			LiteralNode.IntegerLiteral(literal.value).finish()
		}
}

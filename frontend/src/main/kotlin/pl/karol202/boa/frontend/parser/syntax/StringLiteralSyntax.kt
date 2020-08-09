package pl.karol202.boa.frontend.parser.syntax

import pl.karol202.boa.ast.LiteralNode
import pl.karol202.boa.frontend.lexer.Token

object StringLiteralSyntax : AbstractSyntax<LiteralNode.StringLiteral>()
{
	override fun SyntaxScope.syntax() =
		token<Token.Literal.String>() then { literal ->
			LiteralNode.StringLiteral(literal.value).finish()
		}
}

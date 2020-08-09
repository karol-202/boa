package pl.karol202.boa.frontend.parser.syntax

import pl.karol202.boa.ast.LiteralNode
import pl.karol202.boa.frontend.lexer.Token

object RealLiteralSyntax : AbstractSyntax<LiteralNode.RealLiteral>()
{
	override fun SyntaxScope.syntax() =
		token<Token.Literal.Real>() then { literal ->
			LiteralNode.RealLiteral(literal.value).finish()
		}
}

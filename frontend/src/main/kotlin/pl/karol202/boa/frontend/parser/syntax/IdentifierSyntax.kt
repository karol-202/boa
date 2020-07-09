package pl.karol202.boa.frontend.parser.syntax

import pl.karol202.boa.ast.IdentifierNode
import pl.karol202.boa.frontend.lexer.Token

object IdentifierSyntax : AbstractSyntax<IdentifierNode>()
{
	override fun SyntaxScope.syntax() =
		token<Token.Identifier>() then { identifier ->
			IdentifierNode(identifier.name).finish()
		}
}

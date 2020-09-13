package pl.karol202.boa.frontend.parser.syntax

import pl.karol202.boa.ast.ParameterNode
import pl.karol202.boa.frontend.lexer.Token

object ParameterSyntax : AbstractSyntax<ParameterNode>()
{
	override fun SyntaxScope.syntax() =
		syntax(IdentifierSyntax) then { name ->
			token<Token.Special>() matching { it.value == ":" } then {
				syntax(IdentifierSyntax) then { type ->
					ParameterNode(name, type).finish()
				}
			}
		}
}

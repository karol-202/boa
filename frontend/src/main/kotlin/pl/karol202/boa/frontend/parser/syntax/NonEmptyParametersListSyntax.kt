package pl.karol202.boa.frontend.parser.syntax

import pl.karol202.boa.ast.ParametersListNode
import pl.karol202.boa.frontend.lexer.Token

object NonEmptyParametersListSyntax : AbstractSyntax<ParametersListNode>()
{
	override fun SyntaxScope.syntax() =
		syntax(ParameterSyntax) then { firstParameter ->
			either<ParametersListNode> {
				+ token<Token.Comma>().then {
					syntax(NonEmptyParametersListSyntax).then { rest ->
						ParametersListNode(firstParameter, *rest.list.toTypedArray()).finish()
					}
				}
				+ ParametersListNode(firstParameter).finish()
			}
		}
}

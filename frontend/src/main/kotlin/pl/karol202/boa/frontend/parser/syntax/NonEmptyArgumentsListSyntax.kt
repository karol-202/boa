package pl.karol202.boa.frontend.parser.syntax

import pl.karol202.boa.ast.ArgumentsListNode
import pl.karol202.boa.frontend.lexer.Token

object NonEmptyArgumentsListSyntax : AbstractSyntax<ArgumentsListNode>()
{
	override fun SyntaxScope.parse() =
		syntax(ExpressionSyntax).then { firstParameter ->
			either<ArgumentsListNode> {
				+ token<Token.Comma>().then {
					syntax(NonEmptyArgumentsListSyntax).then { rest ->
						ArgumentsListNode(firstParameter, *rest.parameters.toTypedArray()).finish()
					}
				}
				+ ArgumentsListNode(firstParameter).finish()
			}
		}
}

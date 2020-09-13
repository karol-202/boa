package pl.karol202.boa.frontend.parser.syntax

import pl.karol202.boa.ast.ArgumentsListNode
import pl.karol202.boa.frontend.lexer.Token

object NonEmptyArgumentsListSyntax : AbstractSyntax<ArgumentsListNode>()
{
	override fun SyntaxScope.syntax() =
		syntax(ExpressionSyntax).then { firstArgument ->
			either<ArgumentsListNode> {
				+ token<Token.Comma>().then {
					syntax(NonEmptyArgumentsListSyntax).then { rest ->
						ArgumentsListNode(firstArgument, *rest.list.toTypedArray()).finish()
					}
				}
				+ ArgumentsListNode(firstArgument).finish()
			}
		}
}

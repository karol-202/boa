package pl.karol202.boa.frontend.parser.syntax

import pl.karol202.boa.ast.InvocationNode
import pl.karol202.boa.frontend.lexer.Token

object InvocationSyntax : AbstractSyntax<InvocationNode>()
{
	override fun SyntaxScope.syntax() =
		syntax(InvocableSyntax) then { invocable ->
			token<Token.Parenthesis.Open>() then {
				syntax(ArgumentsListSyntax) then { arguments ->
					token<Token.Parenthesis.Close>() then {
						InvocationNode(invocable, arguments).finish()
					}
				}
			}
		}
}

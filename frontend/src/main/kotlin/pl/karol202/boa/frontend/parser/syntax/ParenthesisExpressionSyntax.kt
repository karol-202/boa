package pl.karol202.boa.frontend.parser.syntax

import pl.karol202.boa.ast.ExpressionNode
import pl.karol202.boa.frontend.lexer.Token

object ParenthesisExpressionSyntax : AbstractSyntax<ExpressionNode>()
{
	override fun SyntaxScope.syntax() =
		token<Token.Parenthesis.Open>() then {
			syntax(ExpressionSyntax) then { expression ->
				token<Token.Parenthesis.Close>() then {
					expression.finish()
				}
			}
		}
}

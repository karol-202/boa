package pl.karol202.boa.frontend.parser.syntax

import pl.karol202.boa.ast.AssignmentNode
import pl.karol202.boa.ast.IdentifierNode
import pl.karol202.boa.frontend.lexer.Token

object AssignmentSyntax : AbstractSyntax<AssignmentNode>()
{
	override fun SyntaxScope.syntax() =
		syntax(AssignmentTargetSyntax) then { target ->
			token<Token.Special>() matching { it.value == "=" } then {
				syntax(ExpressionSyntax) then { value ->
					AssignmentNode(target, value).finish()
				}
			}
		}
}

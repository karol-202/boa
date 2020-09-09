package pl.karol202.boa.frontend.parser.syntax

import pl.karol202.boa.ast.IfNode
import pl.karol202.boa.frontend.lexer.Token
import pl.karol202.boa.syntax.KeywordType

object IfSyntax : AbstractSyntax<IfNode>()
{
	override fun SyntaxScope.syntax() =
		syntax(IfConditionalClauseSyntax) then { conditionalClause ->
			syntax(OptionalNewlinesSyntax) then {
				either<IfNode> {
					+ token<Token.Keyword>().matching { it.type == KeywordType.ELSE }.then {
						syntax(IfSyntax) then { restOfIf ->
							IfNode(conditionClauses = listOf(conditionalClause) + restOfIf.conditionClauses,
							       fallbackClause = restOfIf.fallbackClause).finish()
						}
					}
					+ syntax(IfFallbackClauseSyntax).then { elseClause ->
						IfNode(conditionClauses = listOf(conditionalClause),
						       fallbackClause = elseClause).finish()
					}
					+ IfNode(conditionClauses = listOf(conditionalClause)).finish()
				}
			}
		}
}

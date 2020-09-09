package pl.karol202.boa.frontend.parser.syntax

import pl.karol202.boa.ast.IfNode
import pl.karol202.boa.ast.StatementSequenceNode
import pl.karol202.boa.frontend.lexer.Token
import pl.karol202.boa.syntax.KeywordType

object IfConditionalClauseSyntax : AbstractSyntax<IfNode.Clause.Condition>()
{
	override fun SyntaxScope.syntax() =
		token<Token.Keyword>() matching { it.type == KeywordType.IF } then {
			token<Token.Parenthesis.Open>() then {
				syntax(ExpressionSyntax) then { expression ->
					token<Token.Parenthesis.Close>() then {
						syntax(OptionalNewlinesSyntax) then {
							either<IfNode.Clause.Condition> {
								+ token<Token.Block.Open>().then {
									syntax(StatementSequenceSyntax) then { statementSequence ->
										token<Token.Block.Close>() then {
											IfNode.Clause.Condition(expression, statementSequence).finish()
										}
									}
								}
								+ syntax(StatementSyntax).then { statement ->
									IfNode.Clause.Condition(expression, StatementSequenceNode(statement)).finish()
								}
							}
						}
					}
				}
			}
		}
}

package pl.karol202.boa.frontend.parser.syntax

import pl.karol202.boa.ast.IfNode
import pl.karol202.boa.ast.StatementSequenceNode
import pl.karol202.boa.frontend.lexer.Token
import pl.karol202.boa.syntax.KeywordType

object IfFallbackClauseSyntax : AbstractSyntax<IfNode.Clause.Fallback>()
{
	override fun SyntaxScope.syntax() =
		token<Token.Keyword>() matching { it.type == KeywordType.ELSE } then {
			syntax(OptionalNewlinesSyntax) then {
				either<IfNode.Clause.Fallback> {
					+ token<Token.Block.Open>().then {
						syntax(StatementSequenceSyntax) then { statementSequence ->
							token<Token.Block.Close>() then {
								IfNode.Clause.Fallback(statementSequence).finish()
							}
						}
					}
					+ syntax(StatementSyntax).then { statement ->
						IfNode.Clause.Fallback(StatementSequenceNode(statement)).finish()
					}
				}
			}
		}
}

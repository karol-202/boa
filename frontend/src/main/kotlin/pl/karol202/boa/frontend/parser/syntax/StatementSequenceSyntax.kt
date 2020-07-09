package pl.karol202.boa.frontend.parser.syntax

import pl.karol202.boa.ast.StatementSequenceNode
import pl.karol202.boa.frontend.lexer.Token

object StatementSequenceSyntax : AbstractSyntax<StatementSequenceNode>()
{
	override fun SyntaxScope.syntax() =
		either<StatementSequenceNode> {
			+ syntax(StatementSyntax).then { firstStatement ->
				syntax(IgnoreTokensUntilSyntax { it is Token.Newline }).then {
					syntax(StatementSequenceSyntax).then { rest ->
						StatementSequenceNode(firstStatement, *rest.statements.toTypedArray()).finish()
					}
				}
			}
			+ StatementSequenceNode().finish()
		}
}

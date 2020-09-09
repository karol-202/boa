package pl.karol202.boa.frontend.parser.syntax

import pl.karol202.boa.ast.StatementSequenceNode

object StatementSequenceSyntax : AbstractSyntax<StatementSequenceNode>()
{
	override fun SyntaxScope.syntax() =
		syntax(OptionalNewlinesSyntax) then {
			either<StatementSequenceNode> {
				+ syntax(StatementSyntax).then { firstStatement ->
					syntax(StatementSequenceSyntax).then { rest ->
						StatementSequenceNode(firstStatement, *rest.list.toTypedArray()).finish()
					}
				}
				+ StatementSequenceNode().finish()
			}
		}
}

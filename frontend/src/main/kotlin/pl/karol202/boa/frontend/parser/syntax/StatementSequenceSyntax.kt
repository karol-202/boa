package pl.karol202.boa.frontend.parser.syntax

import pl.karol202.boa.ast.StatementNode
import pl.karol202.boa.ast.StatementSequenceNode
import pl.karol202.boa.frontend.lexer.Token

object StatementSequenceSyntax : AbstractSyntax<StatementSequenceNode>()
{
	override fun SyntaxScope.syntax() =
		either<StatementSequenceNode> {
			+ syntax(StatementSyntax).then { firstStatement ->
				ignoreThenRest(firstStatement)
			}
			+ ignoreThenRest()
			+ StatementSequenceNode().finish()
		}

	private fun SyntaxScope.ignoreThenRest(vararg statements: StatementNode) = syntax(IgnoreTokensUntilSyntax { it is Token.Newline }).then {
		syntax(StatementSequenceSyntax).then { rest ->
			StatementSequenceNode(*statements, *rest.list.toTypedArray()).finish()
		}
	}
}

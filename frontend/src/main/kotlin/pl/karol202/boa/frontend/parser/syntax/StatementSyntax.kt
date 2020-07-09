package pl.karol202.boa.frontend.parser.syntax

import pl.karol202.boa.ast.StatementNode

object StatementSyntax : AbstractSyntax<StatementNode>()
{
	override fun SyntaxScope.syntax() =
		either<StatementNode> {
			+ syntax(VariableSyntax).just()
			+ syntax(ExpressionSyntax).just()
		}
}

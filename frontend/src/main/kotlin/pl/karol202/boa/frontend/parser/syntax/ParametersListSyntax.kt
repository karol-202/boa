package pl.karol202.boa.frontend.parser.syntax

import pl.karol202.boa.ast.ParametersListNode

object ParametersListSyntax : AbstractSyntax<ParametersListNode>()
{
	override fun SyntaxScope.syntax() =
		either<ParametersListNode> {
			+ syntax(NonEmptyParametersListSyntax).just()
			+ ParametersListNode().finish()
		}
}

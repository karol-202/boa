package pl.karol202.boa.frontend.parser.syntax

import pl.karol202.boa.ast.ArgumentsListNode

object ArgumentsListSyntax : AbstractSyntax<ArgumentsListNode>()
{
	override fun SyntaxScope.syntax() =
		either<ArgumentsListNode> {
			+ syntax(NonEmptyArgumentsListSyntax).just()
			+ ArgumentsListNode().finish()
		}
}

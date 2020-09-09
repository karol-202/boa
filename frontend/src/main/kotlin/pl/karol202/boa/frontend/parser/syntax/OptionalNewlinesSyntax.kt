package pl.karol202.boa.frontend.parser.syntax

import pl.karol202.boa.ast.Node
import pl.karol202.boa.frontend.lexer.Token

object OptionalNewlinesSyntax : AbstractSyntax<Node>()
{
	private object NothingNode : Node

	override fun SyntaxScope.syntax() =
		either<Node> {
			+ token<Token.Newline>().then {
				syntax(OptionalNewlinesSyntax).just()
			}
			+ NothingNode.finish()
		}
}

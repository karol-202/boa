package pl.karol202.boa.frontend.parser.syntax

import pl.karol202.boa.ast.Node
import pl.karol202.boa.frontend.lexer.Token

class IgnoreTokensUntilSyntax(private val until: (Token) -> Boolean) : AbstractSyntax<Node>()
{
	private object NothingNode : Node

	override fun SyntaxScope.syntax() =
		either<Node> {
			+ token().matching(until).then {
				NothingNode.finish()
			}
			+ token().then {
				syntax(this@IgnoreTokensUntilSyntax).just()
			}
		}
}

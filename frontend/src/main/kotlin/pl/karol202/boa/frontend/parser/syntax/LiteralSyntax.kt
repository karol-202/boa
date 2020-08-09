package pl.karol202.boa.frontend.parser.syntax

import pl.karol202.boa.ast.LiteralNode
import pl.karol202.boa.frontend.lexer.Token

object LiteralSyntax : AbstractSyntax<LiteralNode>()
{
	override fun SyntaxScope.syntax() =
		either<LiteralNode> {
			+ syntax(StringLiteralSyntax).just()
			+ syntax(IntegerLiteralSyntax).just()
			+ syntax(RealLiteralSyntax).just()
		}
}



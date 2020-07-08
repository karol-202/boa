package pl.karol202.boa.frontend.parser.structure

import pl.karol202.boa.ast.IdentifierNode
import pl.karol202.boa.frontend.lexer.Token
import pl.karol202.boa.frontend.parser.ParserIssue

object IdentifierStructure : AbstractStructure<IdentifierNode>()
{
	override fun Builder.parse(): IdentifierNode?
	{
		val identifier = parseToken<Token.Identifier>() ?: return fail(ParserIssue.IdentifierRequired)
		return IdentifierNode(identifier.name)
	}
}

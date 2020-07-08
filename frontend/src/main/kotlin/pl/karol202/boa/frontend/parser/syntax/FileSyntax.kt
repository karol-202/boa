package pl.karol202.boa.frontend.parser.syntax

import pl.karol202.boa.ast.FileNode
import pl.karol202.boa.ast.IdentifierNode
import pl.karol202.boa.frontend.lexer.Token

object FileSyntax : AbstractSyntax<FileNode>()
{
	override fun SyntaxScope.parse() =
		syntax(StatementSyntax).then { statement ->
			FileNode(listOf(statement)).finish()
		}
}

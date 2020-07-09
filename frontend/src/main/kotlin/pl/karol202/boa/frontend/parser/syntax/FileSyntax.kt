package pl.karol202.boa.frontend.parser.syntax

import pl.karol202.boa.ast.FileNode
import pl.karol202.boa.ast.IdentifierNode
import pl.karol202.boa.frontend.lexer.Token

object FileSyntax : AbstractSyntax<FileNode>()
{
	override fun SyntaxScope.syntax() =
		syntax(StatementSequenceSyntax).then { statements ->
			FileNode(statements).finish()
		}
}

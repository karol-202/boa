package pl.karol202.boa.frontend.parser.structure

import pl.karol202.boa.ast.FileNode
import pl.karol202.boa.ast.StatementNode
import pl.karol202.boa.frontend.lexer.Token

object FileStructure : AbstractStructure<FileNode>()
{
	override fun Builder.parse(): FileNode?
	{
		val statements = mutableListOf<StatementNode>()
		while(tokens.contains(Token.Newline))
		{
			val statement = parseStructure(StatementStructure)
			if(statement != null) statements += statement
			else skipUntil { it == Token.Newline }
		}
		return FileNode(statements)
	}
}

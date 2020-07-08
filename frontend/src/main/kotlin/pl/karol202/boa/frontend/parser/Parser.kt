package pl.karol202.boa.frontend.parser

import pl.karol202.boa.Phase
import pl.karol202.boa.ast.FileNode
import pl.karol202.boa.frontend.lexer.Token
import pl.karol202.boa.frontend.parser.structure.FileStructure

class Parser : Phase<List<Token>, FileNode>
{
	override fun process(input: List<Token>) = when(val result = FileStructure.parse(input))
	{
		is ParseResult.Success -> Phase.Result.Success(result.node, result.issues)
		is ParseResult.Failure -> Phase.Result.Failure(result.issues)
	}
}

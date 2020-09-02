package pl.karol202.boa.frontend.parser

import pl.karol202.boa.Phase
import pl.karol202.boa.ast.FileNode
import pl.karol202.boa.frontend.lexer.Token
import pl.karol202.boa.frontend.parser.syntax.FileSyntax

object Parser : Phase<List<Token>, FileNode>
{
	override fun process(input: List<Token>) = when(val result = FileSyntax.parse(input))
	{
		is ParseResult.Success -> Phase.Result.Success(result.node)
		is ParseResult.Failure -> Phase.Result.Failure()
	}
}

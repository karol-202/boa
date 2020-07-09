package pl.karol202.boa.frontend.parser

import pl.karol202.boa.IssueProvider
import pl.karol202.boa.Phase
import pl.karol202.boa.ast.FileNode
import pl.karol202.boa.frontend.lexer.Token
import pl.karol202.boa.frontend.parser.syntax.FileSyntax
import sun.awt.X11.XConstants.Success

class Parser : Phase<List<Token>, FileNode>
{
	sealed class Result : Phase.Result<FileNode>,
	                      IssueProvider by IssueProvider.noIssues
	{
		data class Success(override val result: FileNode,
		                   override val traceTree: TraceElement) : Result()

		data class Failure(override val traceTree: TraceElement) : Result()
		{
			override val result: FileNode? = null
		}

		abstract val traceTree: TraceElement
	}

	override fun process(input: List<Token>) = when(val result = FileSyntax.parse(input))
	{
		is ParseResult.Success -> Result.Success(result.node, result.traceElement)
		is ParseResult.Failure -> Result.Failure(result.traceElement)
	}
}

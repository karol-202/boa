package pl.karol202.boa.frontend.parser

import pl.karol202.boa.IssueProvider
import pl.karol202.boa.Phase
import pl.karol202.boa.ast.FileNode
import pl.karol202.boa.frontend.lexer.Token
import pl.karol202.boa.frontend.parser.syntax.FileSyntax

object Parser : Phase<List<Token>, FileNode>
{
	sealed class Result<out O> : Phase.Result<O>,
	                             IssueProvider by IssueProvider.noIssues
	{
		data class Success(override val value: FileNode,
		                   override val traceTree: TraceElement) : Result<FileNode>(),
                                                                   Phase.Result.Success<FileNode>

		data class Failure(override val traceTree: TraceElement) : Result<Nothing>(),
		                                                           Phase.Result.Failure

		abstract val traceTree: TraceElement
	}

	override fun process(input: List<Token>) = when(val result = FileSyntax.parse(input))
	{
		is ParseResult.Success -> Result.Success(result.node, result.traceElement)
		is ParseResult.Failure -> Result.Failure(result.traceElement)
	}
}

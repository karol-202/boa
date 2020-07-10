package pl.karol202.boa.interpreter

import pl.karol202.boa.IssueProvider
import pl.karol202.boa.Phase
import pl.karol202.boa.ast.FileNode

object Interpreter : Phase<FileNode, Program>
{
	data class Result(override val value: Program) : Phase.Result.Success<Program>,
	                                                 IssueProvider by IssueProvider.noIssues

	override fun process(input: FileNode) = Result(InterpreterProgram(input))
}

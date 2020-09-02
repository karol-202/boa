package pl.karol202.boa.interpreter

import pl.karol202.boa.Phase
import pl.karol202.boa.ast.FileNode

object Interpreter : Phase<FileNode, Program>
{
	override fun process(input: FileNode) = Phase.Result.Success(InterpreterProgram(input))
}

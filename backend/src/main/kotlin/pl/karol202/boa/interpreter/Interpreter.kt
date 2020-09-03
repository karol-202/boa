package pl.karol202.boa.interpreter

import pl.karol202.boa.Phase
import pl.karol202.boa.ast.FileWithImportsNode
import pl.karol202.boa.interpreter.program.ProgramImpl
import pl.karol202.boa.interpreter.program.Program

object Interpreter : Phase<List<FileWithImportsNode>, Program>
{
	override fun process(input: List<FileWithImportsNode>) = Phase.Result.Success(ProgramImpl(input))
}

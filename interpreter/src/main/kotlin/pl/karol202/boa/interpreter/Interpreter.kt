package pl.karol202.boa.interpreter

import pl.karol202.boa.Phase
import pl.karol202.boa.ast.DependencyNode
import pl.karol202.boa.ast.FileWithImportsNode
import pl.karol202.boa.interpreter.program.ProgramImpl
import pl.karol202.boa.interpreter.program.Program

object Interpreter : Phase<DependencyNode, Program>
{
	override fun process(input: DependencyNode) = Phase.Result.Success(ProgramImpl(input))
}

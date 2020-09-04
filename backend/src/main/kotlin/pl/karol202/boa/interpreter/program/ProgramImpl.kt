package pl.karol202.boa.interpreter.program

import pl.karol202.boa.ast.FileNode
import pl.karol202.boa.ast.FileWithImportsNode
import pl.karol202.boa.interpreter.DependencyNode
import pl.karol202.boa.interpreter.createDependencyGraph
import pl.karol202.boa.interpreter.data.BuiltinInvocable
import pl.karol202.boa.interpreter.data.InterpreterContext
import pl.karol202.boa.interpreter.data.Variable
import pl.karol202.boa.interpreter.handler.FileHandler
import pl.karol202.boa.syntax.VariableType
import java.io.InputStream
import java.io.OutputStream

private val DEFAULT_VARIABLES = mapOf(
	"print" to Variable(VariableType.IMMUTABLE, BuiltinInvocable { args ->
		requireIO().output.writer().run {
			appendLine(args[0].toString())
			flush()
		}
	})
)

class ProgramImpl(private val fileNodes: List<FileWithImportsNode>) : Program
{
	override fun execute(input: InputStream, output: OutputStream)
	{
		val io = InterpreterContext.IO(input, output)
		val baseContext = InterpreterContext(io, DEFAULT_VARIABLES)
		val rootDependency = createDependencyGraph(fileNodes)
		baseContext.executeFileWithDependencies(rootDependency)
	}

	// TODO Limit visibility of declarations from other files according to import statements
	private fun InterpreterContext.executeFileWithDependencies(node: DependencyNode): InterpreterContext =
		node.dependencies.fold(this) { context, dependency ->
			context.executeFileWithDependencies(dependency)
		}.executeFile(node.fileNode)

	private fun InterpreterContext.executeFile(node: FileNode) = handle(FileHandler, node).context
}

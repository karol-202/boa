package pl.karol202.boa.interpreter.program

import pl.karol202.boa.ast.DependencyNode
import pl.karol202.boa.ast.FileNode
import pl.karol202.boa.interpreter.data.BuiltinInvocable
import pl.karol202.boa.interpreter.data.InterpreterContext
import pl.karol202.boa.interpreter.data.Variable
import pl.karol202.boa.interpreter.handler.FileHandler
import pl.karol202.boa.syntax.VariableType
import java.io.InputStream
import java.io.OutputStream

private val DEFAULT_VARIABLES = mapOf(
	"printLine" to Variable(VariableType.IMMUTABLE, BuiltinInvocable { args ->
		requireIO().output.writer().run {
			appendLine(args[0].toString())
			flush()
		}
	})
)

class ProgramImpl(private val rootDependency: DependencyNode) : Program
{
	private data class State(val baseContext: InterpreterContext,
	                         val fileDeltaContexts: Map<FileNode, InterpreterContext> = emptyMap(),
	                         val currentContext: InterpreterContext = baseContext)
	{
		fun withFileDeltaContext(fileNode: FileNode, deltaContext: InterpreterContext) =
			copy(fileDeltaContexts = fileDeltaContexts + (fileNode to deltaContext))

		fun withFileDeltaContextAddedToCurrentContext(fileNode: FileNode) =
			copy(currentContext = currentContext + requireFileDeltaContext(fileNode))

		fun withFilesDeltaContextsAddedToCurrentContext(fileNodes: List<FileNode>) =
			fileNodes.fold(this) { state, node -> state.withFileDeltaContextAddedToCurrentContext(node) }

		fun withCurrentContextReset() = copy(currentContext = baseContext)

		fun requireFileDeltaContext(fileNode: FileNode) = fileDeltaContexts[fileNode] ?: error("No file context: $fileNode")
	}

	override fun execute(input: InputStream, output: OutputStream)
	{
		val io = InterpreterContext.IO(input, output)
		val baseContext = InterpreterContext(io, DEFAULT_VARIABLES)
		State(baseContext).executeDependency(rootDependency)
	}

	private fun State.executeDependency(node: DependencyNode): State = this
		.ensureDependenciesExecuted(node.dependencies)
		.withFilesDeltaContextsAddedToCurrentContext(node.dependencies.map { it.fileNode })
		.executeFile(node.fileNode)
		.withCurrentContextReset()

	private fun State.ensureDependenciesExecuted(dependencies: List<DependencyNode>) =
		dependencies.fold(this) { state, dependency ->
			state.ensureDependencyExecuted(dependency)
		}

	private fun State.ensureDependencyExecuted(node: DependencyNode) =
		if(node.fileNode in fileDeltaContexts) this
		else executeDependency(node)

	private fun State.executeFile(node: FileNode) =
		withFileDeltaContext(node, deltaContext = currentContext.handle(FileHandler, node).context - currentContext)
}

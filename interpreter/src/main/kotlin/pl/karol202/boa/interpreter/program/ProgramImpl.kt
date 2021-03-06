package pl.karol202.boa.interpreter.program

import pl.karol202.boa.ast.DependencyNode
import pl.karol202.boa.ast.FileNode
import pl.karol202.boa.interpreter.data.InterpreterContext
import pl.karol202.boa.interpreter.data.MemberLocation
import pl.karol202.boa.interpreter.data.Variable
import pl.karol202.boa.interpreter.handler.FileHandler
import pl.karol202.boa.interpreter.value.*
import pl.karol202.boa.syntax.VariableMutability
import pl.karol202.boa.type.*
import java.io.InputStream
import java.io.OutputStream
import kotlin.math.sqrt

private val DEFAULT_CONTEXT = InterpreterContext()
	.withVariable("Any", Variable(
		mutability = VariableMutability.IMMUTABLE,
		type = TypeType,
		value = TypeValue(AnyType)
	))
	.withVariable("Void", Variable(
		mutability = VariableMutability.IMMUTABLE,
		type = TypeType,
		value = TypeValue(VoidType)
	))
	.withVariable("Bool", Variable(
		mutability = VariableMutability.IMMUTABLE,
		type = TypeType,
		value = TypeValue(BoolType)
	))
	.withVariable("Int", Variable(
		mutability = VariableMutability.IMMUTABLE,
		type = TypeType,
		value = TypeValue(IntType)
	))
	.withVariable("Real", Variable(
		mutability = VariableMutability.IMMUTABLE,
		type = TypeType,
		value = TypeValue(RealType)
	))
	.withVariable("String", Variable(
		mutability = VariableMutability.IMMUTABLE,
		type = TypeType,
		value = TypeValue(StringType)
	))
	.withVariable("Type", Variable(
		mutability = VariableMutability.IMMUTABLE,
		type = TypeType,
		value = TypeValue(TypeType)
	))
	.withVariable("readLine", builtinFunctionVariable(parameterTypes = emptyList(),
	                                                  returnType = StringType) {
		StringValue(requireIO().input.bufferedReader().readLine())
	})
	.withVariable("printLine", builtinFunctionVariable(parameterTypes = listOf(AnyType),
                                                        returnType = VoidType) { args ->
		requireIO().output.writer().let {
			it.appendLine(args[0].toStringFunction.invoke(this).requireToBe<StringValue>(StringType).value)
			it.flush()
		}
		VoidValue
	})
	.withVariable("parseInt", builtinFunctionVariable(parameterTypes = listOf(StringType),
	                                                  returnType = IntType) { args ->
		IntValue(args[0].requireToBe<StringValue>(StringType).value.toInt())
	})
	.withVariable("parseReal", builtinFunctionVariable(parameterTypes = listOf(StringType),
	                                                   returnType = RealType) { args ->
		RealValue(args[0].requireToBe<StringValue>(StringType).value.toDouble())
	})
	.withVariable("sqrt", builtinFunctionVariable(parameterTypes = listOf(RealType),
	                                              returnType = RealType) { args ->
		RealValue(sqrt(args[0].requireToBe<RealValue>(RealType).value))
	})

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

		// TODO Raise error in case of conflict between nodes
		fun withFilesDeltaContextsAddedToCurrentContext(fileNodes: List<FileNode>) =
			fileNodes.fold(this) { state, node -> state.withFileDeltaContextAddedToCurrentContext(node) }

		fun withCurrentContextReset() = copy(currentContext = baseContext)

		fun requireFileDeltaContext(fileNode: FileNode) = fileDeltaContexts[fileNode] ?: error("No file context: $fileNode")
	}

	override fun execute(input: InputStream, output: OutputStream)
	{
		val ioContext = InterpreterContext().withIO(InterpreterContext.IO(input, output))
		val baseContext = DEFAULT_CONTEXT + ioContext
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

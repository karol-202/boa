package pl.karol202.boa.cli

import pl.karol202.boa.Issue
import pl.karol202.boa.Phase
import pl.karol202.boa.ast.FileNode
import pl.karol202.boa.ast.FileWithImportsNode
import pl.karol202.boa.frontend.transformer.ImportsExtractor
import java.io.File

object SourcesResolver : Phase<File, List<FileWithImportsNode>>
{
	private data class FileNodeAndImports(val fileNode: FileNode,
	                                      val imports: List<File>)

	private sealed class State
	{
		data class Processing(val files: Map<File, FileNodeAndImports> = emptyMap(),
		                      val issues: List<Issue> = emptyList()) : State()
		{
			override fun ifProcessing(transform: Processing.() -> State) = transform(this)

			fun wasFileProcessed(file: File) = file in files.keys

			fun withFile(file: File, nodeAndImports: FileNodeAndImports) =
				copy(files = files + (file to nodeAndImports))

			fun fail() = Failed(issues)
		}

		data class Failed(val issues: List<Issue> = emptyList()) : State()
		{
			override fun ifProcessing(transform: Processing.() -> State) = this
		}

		abstract fun ifProcessing(transform: Processing.() -> State): State
	}

	override fun process(input: File) = State.Processing().processFile(input).toResult()

	private fun State.processFile(file: File): State = ifProcessing {
		if(wasFileProcessed(file)) this
		else FilePipeline.process(file).fold(
			ifSuccess = { handleProcessedFile(file, it.resolvePaths()) },
			ifFailure = { fail() }
		)
	}

	private fun State.Processing.handleProcessedFile(file: File, result: FileNodeAndImports): State
	{
		val initialState = withFile(file, result) as State
		return result.imports.fold(initialState) { state, fileToImport ->
			state.processFile(fileToImport)
		}
	}

	private fun State.toResult() = when(this)
	{
		is State.Processing -> Phase.Result.Success(files.values.map { createFileWithImportsNode(it) }, issues)
		is State.Failed -> Phase.Result.Failure(issues)
	}

	private fun State.Processing.createFileWithImportsNode(nodeAndImports: FileNodeAndImports) =
		FileWithImportsNode(nodeAndImports.fileNode, nodeAndImports.imports.map { requireFileNode(it) })

	private fun State.Processing.requireFileNode(file: File) = files[file]?.fileNode ?: error("File not found")

	private fun ImportsExtractor.FileNodeAndImports.resolvePaths() = FileNodeAndImports(fileNode, imports.map { File(it) })
}

package pl.karol202.boa.frontend.transformer

import pl.karol202.boa.Phase
import pl.karol202.boa.ast.FileNode
import pl.karol202.boa.ast.ImportNode

object ImportsExtractor : Phase<FileNode, ImportsExtractor.FileNodeAndImports>
{
	data class FileNodeAndImports(val fileNode: FileNode,
	                              val imports: List<String>)

	override fun process(input: FileNode): Phase.Result.Success<FileNodeAndImports>
	{
		val importNodes = input.statements.list.mapNotNull { it as? ImportNode }
		val importPaths = importNodes.map { it.path }

		val fileNode = input.copy(statements = input.statements.copy(list = input.statements.list - importNodes))
		return Phase.Result.Success(FileNodeAndImports(fileNode, importPaths))
	}
}

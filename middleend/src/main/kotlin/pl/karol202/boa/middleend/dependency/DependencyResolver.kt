package pl.karol202.boa.middleend.dependency

import pl.karol202.boa.Issue
import pl.karol202.boa.Phase
import pl.karol202.boa.ast.DependencyNode
import pl.karol202.boa.ast.FileNode
import pl.karol202.boa.ast.FileWithImportsNode

object DependencyResolver : Phase<List<FileWithImportsNode>, DependencyNode>
{
	object NoRootDependencyIssue : Issue(Level.ERROR, "Cannot find root dependency node")

	private sealed class DependencyException(val issue: Issue) : RuntimeException()
	{
		class NoRootDependency : DependencyException(NoRootDependencyIssue)
	}

	private data class State(val inputNodes: List<FileWithImportsNode>,
	                         val dependencyNodes: List<DependencyNode> = emptyList())
	{
		fun withDependencyNode(node: DependencyNode) = copy(dependencyNodes = dependencyNodes + node)
	}

	override fun process(input: List<FileWithImportsNode>) =
		try { Phase.Result.Success(createDependencyGraph(input)) }
		catch(e: DependencyException) { Phase.Result.Failure(e.issue) }

	private fun createDependencyGraph(inputNodes: List<FileWithImportsNode>) =
		inputNodes.fold(State(inputNodes)) { state, nodeWithImports ->
			state.withDependencyNode(state.createDependencyNode(nodeWithImports).second)
		}.findRoot()

	private fun State.createDependencyNode(inputNode: FileWithImportsNode): Pair<State, DependencyNode>
	{
		data class ImportsLookupState(val globalState: State,
		                              val dependencies: List<DependencyNode> = emptyList())
		{
			fun withGlobalState(globalState: State) = copy(globalState = globalState)

			fun withDependency(node: DependencyNode) = copy(dependencies = dependencies + node)
		}

		val lookupResult = inputNode.imports.fold(ImportsLookupState(this)) { lookupState, import ->
			val foundNode = findDependencyNode(import)
			if(foundNode != null) lookupState.withDependency(foundNode)
			else
			{
				val importInputNode = findInputNode(import) ?: error("Unexpected dependency: $import")
				val (newGlobalState, createdNode) = lookupState.globalState.createDependencyNode(importInputNode)
				lookupState.withGlobalState(newGlobalState).withDependency(createdNode)
			}
		}
		val dependencyNode = DependencyNode(inputNode.file, lookupResult.dependencies)
		return lookupResult.globalState to dependencyNode
	}

	private fun State.findInputNode(fileNode: FileNode) = inputNodes.firstOrNull { it.file == fileNode }

	private fun State.findDependencyNode(fileNode: FileNode) = dependencyNodes.firstOrNull { it.fileNode == fileNode }

	private fun State.findRoot() =
		dependencyNodes.singleOrNull { !hasParents(it) } ?: throw DependencyException.NoRootDependency()

	private fun State.hasParents(node: DependencyNode) = dependencyNodes.any { node in it.dependencies }
}

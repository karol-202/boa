package pl.karol202.boa.interpreter

import pl.karol202.boa.ast.FileNode

// Issues related to internal bug of interpreter
sealed class InterpreterError(message: String? = null) : Exception(message)
{
	class NoIO : InterpreterError()

	class UnexpectedDependency(fileNode: FileNode) : InterpreterError(fileNode.toString())
}

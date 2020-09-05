package pl.karol202.boa.interpreter

import pl.karol202.boa.ast.Node
import kotlin.reflect.KClass

sealed class InterpreterException(message: String? = null) : RuntimeException(message)
{
	class UnexpectedNode(expected: KClass<out Node>, actual: KClass<out Node>) :
		InterpreterException("Cannot handle ${actual.simpleName} as: ${expected.simpleName}")

	class TypeError(expected: KClass<*>, actual: KClass<*>) :
		InterpreterException("Expected: ${expected.simpleName}, actual: ${actual.simpleName}")

	class UnknownIdentifier(identifier: String) :
		InterpreterException(identifier)

	class ImportNotSupported :
		InterpreterException("Imports are not supported by backend")

	class IllegalAssignment(identifier: String) :
		InterpreterException(identifier)

	class VariableAlreadyDeclared(identifier: String) :
		InterpreterException(identifier)
}

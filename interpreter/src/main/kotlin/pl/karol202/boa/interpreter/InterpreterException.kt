package pl.karol202.boa.interpreter

import pl.karol202.boa.ast.Node
import pl.karol202.boa.type.Type
import kotlin.reflect.KClass

sealed class InterpreterException(message: String? = null) : RuntimeException(message)
{
	class NotImplemented(message: String) :
		InterpreterException(message)

	class UnexpectedNode(expected: KClass<out Node>, actual: KClass<out Node>) :
		InterpreterException("Cannot handle ${actual.simpleName} as: ${expected.simpleName}")

	class TypeError(expected: Type, actual: Type) :
		InterpreterException("Expected: ${expected.displayName}, actual: ${actual.displayName}")

	class UnknownIdentifier(identifier: String) :
		InterpreterException(identifier)

	class ImportNotSupported :
		InterpreterException("Imports should be resolved in middleend level")

	class IllegalAssignment(name: String) :
		InterpreterException(name)

	class VariableAlreadyDeclared(name: String) :
		InterpreterException(name)

	class ArgumentsCountMismatch(expected: Int, actual: Int) :
		InterpreterException("Expected $expected arguments, got: $actual")

	class CannotInvoke(type: Type) :
		InterpreterException("Cannot invoke: $type")
}

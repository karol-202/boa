package pl.karol202.boa.interpreter

import pl.karol202.boa.ast.Node
import kotlin.reflect.KClass

sealed class InterpreterException(message: String) : RuntimeException(message)
{
	class UnexpectedNode(expected: KClass<out Node>, actual: KClass<out Node>) :
		InterpreterException("Cannot handle ${actual.simpleName} as: ${expected.simpleName}")

	class TypeError(expected: KClass<*>, actual: KClass<*>) :
		InterpreterException("Expected: ${expected.simpleName}, actual: ${actual.simpleName}")

	class UnknownIdentifier(identifier: String) :
		InterpreterException(identifier)
}

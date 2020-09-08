package pl.karol202.boa.interpreter

import pl.karol202.boa.ast.Node
import pl.karol202.boa.interpreter.data.MemberLocation
import pl.karol202.boa.syntax.OperatorType
import pl.karol202.boa.type.Type
import kotlin.reflect.KClass

sealed class InterpreterException(message: String? = null) : RuntimeException(message)
{
	class NotSupported(message: String) :
		InterpreterException(message)

	class UnexpectedNode(expected: KClass<out Node>, actual: KClass<out Node>) :
		InterpreterException("Cannot handle ${actual.simpleName} as: ${expected.simpleName}")

	class TypeError(expected: Type, actual: Type) :
		InterpreterException("Expected: ${expected.displayName}, actual: ${actual.displayName}")

	class VariableNotFound(identifier: String) :
		InterpreterException(identifier)

	class MemberNotFound(location: MemberLocation) :
		InterpreterException(location.toString())

	class IllegalAssignment(name: String) :
		InterpreterException(name)

	class VariableAlreadyDeclared(name: String) :
		InterpreterException(name)

	class CannotInvoke(type: Type) :
		InterpreterException("Cannot invoke: $type")

	class ArgumentsMismatch(expected: List<Type>, actual: List<Type>) :
		InterpreterException("Expected (${expected.joinToString()}) arguments, got: (${actual.joinToString()})")
}

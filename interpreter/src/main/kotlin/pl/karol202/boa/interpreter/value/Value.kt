package pl.karol202.boa.interpreter.value

import pl.karol202.boa.interpreter.InterpreterException
import pl.karol202.boa.interpreter.data.MemberLocation
import pl.karol202.boa.interpreter.data.Variable
import pl.karol202.boa.type.Type

interface Value
{
	val type: Type
	val members: Map<MemberLocation, Variable>
}

inline fun <reified T> Value.requireToBe(expected: Type) = this as? T ?: throw InterpreterException.TypeError(expected, type)

fun Value.requireToBeFunction() = this as? FunctionValue ?: throw InterpreterException.CannotInvoke(type)

fun Value.requireMember(location: MemberLocation) =
	members[location]?.value ?: throw InterpreterException.MemberNotFound(location)

val Value.toStringFunction get() = requireMember(MemberLocation.Name("toString")).requireToBeFunction()

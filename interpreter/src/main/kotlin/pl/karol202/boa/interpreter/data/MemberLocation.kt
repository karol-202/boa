package pl.karol202.boa.interpreter.data

import pl.karol202.boa.syntax.OperatorType

sealed class MemberLocation
{
	data class Name(val name: String) : MemberLocation()
	{
		override fun toString() = name
	}

	data class Operator(val type: OperatorType) : MemberLocation()
	{
		override fun toString() = type.name
	}
}

package pl.karol202.boa.interpreter.data

import pl.karol202.boa.syntax.VariableType

data class Variable(val type: VariableType,
                    val value: Any)
{
	fun withValue(value: Any) = copy(value = value)
}

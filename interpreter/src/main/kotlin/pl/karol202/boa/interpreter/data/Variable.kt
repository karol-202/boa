package pl.karol202.boa.interpreter.data

import pl.karol202.boa.type.Type
import pl.karol202.boa.interpreter.value.Value
import pl.karol202.boa.syntax.VariableMutability

data class Variable(val mutability: VariableMutability,
                    val type: Type,
                    val value: Value)
{
	fun withValue(value: Value) = copy(value = value)
}

package pl.karol202.boa.interpreter.value.function

import pl.karol202.boa.interpreter.data.InterpreterContext
import pl.karol202.boa.interpreter.value.Value

interface FunctionValue : Value
{
	fun invoke(context: InterpreterContext, args: List<Value>): Value
}

fun FunctionValue.invoke(context: InterpreterContext, vararg args: Value) = invoke(context, args.toList())

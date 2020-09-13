package pl.karol202.boa.interpreter.value.function

import pl.karol202.boa.interpreter.data.InterpreterContext
import pl.karol202.boa.interpreter.value.Value
import pl.karol202.boa.interpreter.value.VoidValue
import pl.karol202.boa.type.Type
import pl.karol202.boa.type.VoidType

class BuiltinFunctionValue(override val parameterTypes: List<Type>,
                           override val returnType: Type,
                           private val function: InterpreterContext.(List<Value>) -> Value) : AbstractFunctionValue()
{
	companion object
	{
		fun void(argumentTypes: List<Type>, function: InterpreterContext.(List<Value>) -> Unit) =
			BuiltinFunctionValue(argumentTypes, VoidType) { args ->
				function(args)
				VoidValue
			}
	}

	override fun invoke(context: InterpreterContext, args: List<Value>): Value
	{
		checkArgumentsTypes(args)
		val result = context.function(args)
		checkReturnType(result)
		return result
	}
}

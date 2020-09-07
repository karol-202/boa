package pl.karol202.boa.interpreter.value

import pl.karol202.boa.interpreter.InterpreterException
import pl.karol202.boa.interpreter.data.InterpreterContext
import pl.karol202.boa.type.FunctionType
import pl.karol202.boa.type.Type
import pl.karol202.boa.type.VoidType

interface FunctionValue : Value
{
	fun invoke(context: InterpreterContext, args: List<Value>): Value
}

class BuiltinFunctionValue(private val argumentTypes: List<Type>,
                           private val returnType: Type,
                           private val function: InterpreterContext.(List<Value>) -> Value) : FunctionValue
{
	companion object
	{
		fun void(argumentTypes: List<Type>, function: InterpreterContext.(List<Value>) -> Unit) =
			BuiltinFunctionValue(argumentTypes, VoidType) { args ->
				function(args)
				VoidValue
			}
	}

	override val type get() = FunctionType(argumentTypes, returnType)

	override fun invoke(context: InterpreterContext, args: List<Value>): Value
	{
		checkArgumentsTypes(args)
		val result = context.function(args)
		checkReturnType(result)
		return result
	}

	private fun checkArgumentsTypes(args: List<Value>)
	{
		if(args.size != argumentTypes.size) throw InterpreterException.ArgumentsCountMismatch(argumentTypes.size, args.size)
		args.map { it.type }.zip(argumentTypes).forEach { (act, exp) ->
			if(!act.isAssignableTo(exp)) throw InterpreterException.TypeError(exp, act)
		}
	}

	private fun checkReturnType(result: Value)
	{
		if(result.type != returnType) throw InterpreterException.TypeError(returnType, result.type)
	}
}

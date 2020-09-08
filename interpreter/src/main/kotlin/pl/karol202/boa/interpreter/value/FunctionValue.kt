package pl.karol202.boa.interpreter.value

import pl.karol202.boa.interpreter.InterpreterException
import pl.karol202.boa.interpreter.data.InterpreterContext
import pl.karol202.boa.interpreter.data.MemberLocation
import pl.karol202.boa.interpreter.data.Variable
import pl.karol202.boa.syntax.VariableMutability
import pl.karol202.boa.type.FunctionType
import pl.karol202.boa.type.StringType
import pl.karol202.boa.type.Type
import pl.karol202.boa.type.VoidType

interface FunctionValue : Value
{
	fun invoke(context: InterpreterContext, args: List<Value>): Value
}

fun FunctionValue.invoke(context: InterpreterContext, vararg args: Value) = invoke(context, args.toList())

class BuiltinFunctionValue(private val parameterTypes: List<Type>,
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

	override val type get() = FunctionType(parameterTypes, returnType)
	override val members get() = mapOf<MemberLocation, Variable>(
		MemberLocation.Name("toString") to Variable(
			mutability = VariableMutability.IMMUTABLE,
			type = FunctionType(
				parameterTypes = emptyList(),
				returnType = StringType
			),
			value = BuiltinFunctionValue(
				parameterTypes = emptyList(),
				returnType = StringType
			) { StringValue(type.displayName) }
		)
	)

	override fun invoke(context: InterpreterContext, args: List<Value>): Value
	{
		checkArgumentsTypes(args)
		val result = context.function(args)
		checkReturnType(result)
		return result
	}

	private fun checkArgumentsTypes(args: List<Value>)
	{
		val argumentTypes = args.map { it.type }
		val amountMatches = args.size == parameterTypes.size
		val typesMatches = argumentTypes.zip(parameterTypes).all { (act, exp) -> act.isAssignableTo(exp) }
		if(!amountMatches || !typesMatches) throw InterpreterException.ArgumentsMismatch(parameterTypes, argumentTypes)
	}

	private fun checkReturnType(result: Value)
	{
		if(!result.type.isAssignableTo(returnType)) throw InterpreterException.TypeError(returnType, result.type)
	}
}

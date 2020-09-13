package pl.karol202.boa.interpreter.value.function

import pl.karol202.boa.interpreter.InterpreterException
import pl.karol202.boa.interpreter.data.MemberLocation
import pl.karol202.boa.interpreter.data.Variable
import pl.karol202.boa.interpreter.value.StringValue
import pl.karol202.boa.interpreter.value.Value
import pl.karol202.boa.interpreter.value.builtinFunctionVariable
import pl.karol202.boa.type.FunctionType
import pl.karol202.boa.type.StringType
import pl.karol202.boa.type.Type

abstract class AbstractFunctionValue : FunctionValue
{
	abstract val parameterTypes: List<Type>
	abstract val returnType: Type

	override val type get() = FunctionType(parameterTypes, returnType)
	override val members get() = mapOf<MemberLocation, Variable>(
		MemberLocation.Name("toString") to builtinFunctionVariable(
			parameterTypes = emptyList(),
			returnType = StringType
		) { StringValue(type.displayName) }
	)

	protected fun checkArgumentsTypes(args: List<Value>)
	{
		val argumentTypes = args.map { it.type }
		val amountMatches = args.size == parameterTypes.size
		val typesMatches = argumentTypes.zip(parameterTypes).all { (act, exp) -> act.isAssignableTo(exp) }
		if(!amountMatches || !typesMatches) throw InterpreterException.ArgumentsMismatch(parameterTypes, argumentTypes)
	}

	protected fun checkReturnType(result: Value)
	{
		if(!result.type.isAssignableTo(returnType)) throw InterpreterException.TypeError(returnType, result.type)
	}
}

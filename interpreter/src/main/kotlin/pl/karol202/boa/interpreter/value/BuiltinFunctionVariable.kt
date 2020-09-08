package pl.karol202.boa.interpreter.value

import pl.karol202.boa.interpreter.data.InterpreterContext
import pl.karol202.boa.interpreter.data.Variable
import pl.karol202.boa.syntax.VariableMutability
import pl.karol202.boa.type.FunctionType
import pl.karol202.boa.type.Type

fun builtinFunctionVariable(parameterTypes: List<Type>,
                            returnType: Type,
                            function: InterpreterContext.(List<Value>) -> Value) = Variable(
	mutability = VariableMutability.IMMUTABLE,
	type = FunctionType(
		parameterTypes = parameterTypes,
		returnType = returnType
	),
	value = BuiltinFunctionValue(
		parameterTypes = parameterTypes,
		returnType = returnType,
		function = function
	)
)

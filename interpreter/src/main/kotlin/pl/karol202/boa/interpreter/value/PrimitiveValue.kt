package pl.karol202.boa.interpreter.value

import pl.karol202.boa.interpreter.data.MemberLocation
import pl.karol202.boa.interpreter.data.Variable
import pl.karol202.boa.syntax.OperatorType
import pl.karol202.boa.syntax.VariableMutability
import pl.karol202.boa.type.*

abstract class PrimitiveValue(override val type: Type,
                              override val members: Map<MemberLocation, Variable> = emptyMap()) : Value

object VoidValue : PrimitiveValue(
	type = VoidType,
	members = mapOf(
		MemberLocation.Name("toString") to builtinFunctionVariable(parameterTypes = emptyList(),
		                                                           returnType = StringType) {
			StringValue("Void")
		}
	)
)

class BoolValue(val value: Boolean) : PrimitiveValue(
	type = BoolType,
	members = mapOf(
		MemberLocation.Name("toString") to builtinFunctionVariable(parameterTypes = emptyList(),
		                                                           returnType = StringType) {
			StringValue(value.toString())
		}
	)
)

class IntValue(val value: Int) : PrimitiveValue(
	type = IntType,
	members = mapOf(
		MemberLocation.Name("toString") to builtinFunctionVariable(parameterTypes = emptyList(),
		                                                           returnType = StringType) {
			StringValue(value.toString())
		},
		MemberLocation.Operator(OperatorType.PLUS) to builtinFunctionVariable(parameterTypes = listOf(IntType),
		                                                                      returnType = IntType) { args ->
			val operand = (args[0] as IntValue).value
			IntValue(value + operand)
		}
	)
)

class RealValue(val value: Double) : PrimitiveValue(
	type = RealType,
	members = mapOf(
		MemberLocation.Name("toString") to builtinFunctionVariable(parameterTypes = emptyList(),
		                                                           returnType = StringType) {
			StringValue(value.toString())
		},
		MemberLocation.Operator(OperatorType.PLUS) to builtinFunctionVariable(parameterTypes = listOf(RealType),
		                                                                      returnType = RealType) { args ->
			val operand = (args[0] as RealValue).value
			RealValue(value + operand)
		}
	)
)

class StringValue(val value: String) : PrimitiveValue(
	type = StringType,
	members = mapOf(
		MemberLocation.Name("toString") to builtinFunctionVariable(parameterTypes = emptyList(),
		                                                           returnType = StringType) {
			StringValue(value)
		},
		MemberLocation.Operator(OperatorType.PLUS) to builtinFunctionVariable(parameterTypes = listOf(StringType),
		                                                                      returnType = StringType) { args ->
			val operand = (args[0] as StringValue).value
			StringValue(value + operand)
		},
		MemberLocation.Operator(OperatorType.PLUS) to builtinFunctionVariable(parameterTypes = listOf(AnyType),
		                                                                      returnType = StringType) { args ->
			val operand = args[0].toStringFunction.invoke(this).requireToBe<StringValue>(StringType).value
			StringValue(value + operand)
		}
	),
)

class TypeValue(val value: Type) : PrimitiveValue(
	type = TypeType,
	members = mapOf(
		MemberLocation.Name("toString") to builtinFunctionVariable(parameterTypes = emptyList(),
		                                                           returnType = StringType) {
			StringValue(value.displayName)
		}
	)
)

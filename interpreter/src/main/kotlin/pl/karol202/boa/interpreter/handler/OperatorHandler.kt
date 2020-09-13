package pl.karol202.boa.interpreter.handler

import pl.karol202.boa.ast.OperatorNode
import pl.karol202.boa.interpreter.data.InterpreterContext
import pl.karol202.boa.interpreter.data.MemberLocation
import pl.karol202.boa.interpreter.value.*
import pl.karol202.boa.interpreter.value.function.BuiltinFunctionValue
import pl.karol202.boa.interpreter.value.function.FunctionValue
import pl.karol202.boa.interpreter.value.function.invoke
import pl.karol202.boa.syntax.OperatorType
import pl.karol202.boa.type.AnyType

object OperatorHandler : Handler<OperatorNode, FunctionValue>
{
	override fun InterpreterContext.handle(node: OperatorNode) =
		withResult(BuiltinFunctionValue(parameterTypes = listOf(AnyType, AnyType),
		                                returnType = AnyType) { args ->
			val (first, second) = args
			when(node.type)
			{
				OperatorType.EQUAL -> BoolValue(first == second)
				else -> first.requireMember(MemberLocation.Operator(node.type)).requireToBeFunction().invoke(this, second)
			}
		})
}

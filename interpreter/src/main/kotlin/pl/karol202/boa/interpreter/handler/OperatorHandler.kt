package pl.karol202.boa.interpreter.handler

import pl.karol202.boa.ast.OperatorNode
import pl.karol202.boa.interpreter.data.InterpreterContext
import pl.karol202.boa.interpreter.data.MemberLocation
import pl.karol202.boa.interpreter.value.*
import pl.karol202.boa.syntax.OperatorType
import pl.karol202.boa.type.AnyType

object OperatorHandler : Handler<OperatorNode, FunctionValue>
{
	override fun InterpreterContext.handle(node: OperatorNode) =
		when(node.type.position)
		{
			OperatorType.Position.UNARY_BEFORE -> withResult(BuiltinFunctionValue(parameterTypes = listOf(AnyType),
			                                                                      returnType = AnyType) { args ->
				args[0].requireMember(MemberLocation.Operator(node.type)).requireToBeFunction().invoke(this)
			})
			OperatorType.Position.BINARY -> withResult(BuiltinFunctionValue(parameterTypes = listOf(AnyType, AnyType),
			                                                                returnType = AnyType) { args ->
				val (first, second) = args
				when(node.type)
				{
					OperatorType.EQUAL -> BoolValue(first == second)
					else -> first.requireMember(MemberLocation.Operator(node.type))
						.requireToBeFunction()
						.invoke(this, second)
				}
			})
		}
}

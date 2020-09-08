package pl.karol202.boa.interpreter.handler

import pl.karol202.boa.ast.OperatorNode
import pl.karol202.boa.interpreter.InterpreterException
import pl.karol202.boa.interpreter.data.InterpreterContext
import pl.karol202.boa.interpreter.data.MemberLocation
import pl.karol202.boa.interpreter.value.*
import pl.karol202.boa.syntax.OperatorType
import pl.karol202.boa.type.AnyType

object OperatorHandler : Handler<OperatorNode, FunctionValue>
{
	override fun InterpreterContext.handle(node: OperatorNode) =
		withResult(BuiltinFunctionValue(parameterTypes = listOf(AnyType, AnyType),
		                                returnType = AnyType) { args ->
			val (first, second) = args
			val operatorFunction = first.requireMember(MemberLocation.Operator(OperatorType.PLUS)).requireToBeFunction()
			operatorFunction.invoke(this, second)
		})
}

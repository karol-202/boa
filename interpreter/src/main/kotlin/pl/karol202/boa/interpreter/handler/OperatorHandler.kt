package pl.karol202.boa.interpreter.handler

import pl.karol202.boa.ast.OperatorNode
import pl.karol202.boa.interpreter.InterpreterException
import pl.karol202.boa.interpreter.data.InterpreterContext
import pl.karol202.boa.interpreter.value.BuiltinFunctionValue
import pl.karol202.boa.interpreter.value.FunctionValue
import pl.karol202.boa.syntax.OperatorType
import pl.karol202.boa.type.AnyType
import pl.karol202.boa.type.IntType

object OperatorHandler : Handler<OperatorNode, FunctionValue>
{
	override fun InterpreterContext.handle(node: OperatorNode) =
		withResult(BuiltinFunctionValue(argumentTypes = listOf(AnyType, AnyType),
		                                returnType = AnyType) {
			throw InterpreterException.NotImplemented("Operators")
		})
}

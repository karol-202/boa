package pl.karol202.boa.interpreter.handler

import pl.karol202.boa.ast.LiteralNode
import pl.karol202.boa.interpreter.data.InterpreterContext
import pl.karol202.boa.interpreter.value.IntValue
import pl.karol202.boa.interpreter.value.RealValue
import pl.karol202.boa.interpreter.value.StringValue
import pl.karol202.boa.interpreter.value.Value

object LiteralHandler : Handler<LiteralNode, Value>
{
	override fun InterpreterContext.handle(node: LiteralNode) = when(node)
	{
		is LiteralNode.StringLiteral -> handle(StringLiteralHandler, node)
		is LiteralNode.IntegerLiteral -> handle(IntegerLiteralHandler, node)
		is LiteralNode.RealLiteral -> handle(RealLiteralHandler, node)
	}
}

object StringLiteralHandler : Handler<LiteralNode.StringLiteral, Value>
{
	override fun InterpreterContext.handle(node: LiteralNode.StringLiteral) = withResult(StringValue(node.value))
}

object IntegerLiteralHandler : Handler<LiteralNode.IntegerLiteral, Value>
{
	override fun InterpreterContext.handle(node: LiteralNode.IntegerLiteral) = withResult(IntValue(node.value))
}

object RealLiteralHandler : Handler<LiteralNode.RealLiteral, Value>
{
	override fun InterpreterContext.handle(node: LiteralNode.RealLiteral) = withResult(RealValue(node.value))
}

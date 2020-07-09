package pl.karol202.boa.frontend.parser.syntax

import pl.karol202.boa.ast.OperatorNode
import pl.karol202.boa.frontend.lexer.Token
import pl.karol202.boa.syntax.OperatorType

class OperatorSyntax(private val position: OperatorType.Position) : AbstractSyntax<OperatorNode>()
{
	override fun SyntaxScope.syntax() =
		token<Token.Special>() then { token ->
			val operatorType = OperatorType.find(token.value, position) ?: return@then fail()
			OperatorNode(operatorType).finish()
		}
}

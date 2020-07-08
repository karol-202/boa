package pl.karol202.boa.frontend.parser.syntax

import pl.karol202.boa.ast.ArgumentsListNode
import pl.karol202.boa.ast.ExpressionNode
import pl.karol202.boa.ast.InvocationNode
import pl.karol202.boa.syntax.OperatorType

class OperatorExpressionRestSyntax private constructor(private val leftOperand: ExpressionNode?,
                                                       private val operatorPosition: OperatorType.Position) :
	AbstractSyntax<ExpressionNode>()
{
	companion object
	{
		fun unary() = OperatorExpressionRestSyntax(null, OperatorType.Position.UNARY_BEFORE)

		fun binary(leftOperand: ExpressionNode) = OperatorExpressionRestSyntax(leftOperand, OperatorType.Position.BINARY)
	}

	override fun SyntaxScope.parse() =
		syntax(OperatorSyntax(operatorPosition)) then { operator ->
			syntax(ExpressionOperandSyntax) then { rightOperand ->
				val expression = InvocationNode(operator, ArgumentsListNode(leftOperand, rightOperand))
				either<ExpressionNode> {
					+ syntax(binary(expression)).just()
					+ expression.finish()
				}
			}
		}
}

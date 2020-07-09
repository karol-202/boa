package pl.karol202.boa.frontend.parser.syntax

import pl.karol202.boa.ast.ExpressionNode

object ExpressionSyntax : AbstractSyntax<ExpressionNode>()
{
	override fun SyntaxScope.syntax() =
		syntax(ExpressionOperandSyntax) then { firstOperand ->
			either<ExpressionNode> {
				+ syntax(OperatorExpressionRestSyntax.binary(firstOperand)).just()
				+ firstOperand.finish()
			}
		}
}

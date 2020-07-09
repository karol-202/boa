package pl.karol202.boa.frontend.parser.syntax

import pl.karol202.boa.ast.ExpressionNode

object ExpressionOperandSyntax : AbstractSyntax<ExpressionNode>()
{
	override fun SyntaxScope.syntax() =
		either<ExpressionNode> {
			+ syntax(InvocationSyntax).just()
			+ syntax(ParenthesisExpressionSyntax).just()
			+ syntax(OperatorExpressionRestSyntax.unary()).just()
			+ syntax(LiteralSyntax).just()
			+ syntax(IdentifierSyntax).just()
		}
}

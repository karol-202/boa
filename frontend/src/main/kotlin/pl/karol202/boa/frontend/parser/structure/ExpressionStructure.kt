package pl.karol202.boa.frontend.parser.structure

import pl.karol202.boa.ast.ExpressionNode
import pl.karol202.boa.ast.IdentifierNode
import pl.karol202.boa.ast.OperatorNode
import pl.karol202.boa.frontend.lexer.Token
import pl.karol202.boa.frontend.parser.ParserIssue
import pl.karol202.boa.syntax.OperatorType

object ExpressionStructure : AbstractStructure<ExpressionNode>()
{
	override fun Builder.parse(): ExpressionNode?
	{
		when(val token = parseToken())
		{
			null -> return fail(ParserIssue.ExpressionRequired)
			is Token.Identifier ->
			{
				val identifier = IdentifierNode(token.name)

				when(val nextToken = nextToken)
				{
					is Token.Special ->
					{
						val operatorType = OperatorType.find(nextToken.value, OperatorType.Position.BINARY)
							?: return fail(ParserIssue.InvalidOperator(nextToken.value))
						val operator = OperatorNode(operatorType)
						val rightExpression = parseStructure(ExpressionStructure)
							?: return fail()
						Expr
					}
					is Token.Parenthesis.Open ->
					{

					}
					else -> identifier
				}
			}
			is Token.Literal ->
			{

			}
			is Token.Parenthesis.Open ->
			{

			}
			else -> fail(ParserIssue.UnexpectedToken(token))
		}
	}
}

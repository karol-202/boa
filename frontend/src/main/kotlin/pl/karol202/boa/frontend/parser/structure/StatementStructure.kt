package pl.karol202.boa.frontend.parser.structure

import pl.karol202.boa.ast.StatementNode
import pl.karol202.boa.frontend.parser.ParserIssue

object StatementStructure : AbstractStructure<StatementNode>()
{
	override fun Builder.parse() =
		parseStructure(VariableStructure, required = false) ?:
		parseStructure(ExpressionStructure, required = false) ?:
		fail(ParserIssue.StatementRequired)
}

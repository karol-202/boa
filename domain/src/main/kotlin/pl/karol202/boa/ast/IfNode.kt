package pl.karol202.boa.ast

data class IfNode(val conditionClauses: List<Clause.Condition>,
                  val fallbackClause: Clause.Fallback? = null) : StatementNode
{
	sealed class Clause : Node
	{
		// First ConditionClause corresponds to if-clause and the next ones to else-if-clauses
		data class Condition(val condition: ExpressionNode,
		                     override val block: StatementSequenceNode) : Clause()

		data class Fallback(override val block: StatementSequenceNode) : Clause()

		abstract val block: StatementSequenceNode
	}
}

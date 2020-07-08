package pl.karol202.boa.frontend.parser

import pl.karol202.boa.Issue
import pl.karol202.boa.Issue.Level
import pl.karol202.boa.frontend.lexer.Token

sealed class ParserIssue(override val level: Level,
                         override val message: String) : Issue
{
    class UnexpectedToken(token: Token) : ParserIssue(Level.ERROR,
                                                      "Unexpected token: $token")

    class InvalidOperator(symbol: String) : ParserIssue(Level.ERROR,
                                                        "Invalid operator: $symbol")

    object IdentifierRequired : ParserIssue(Level.ERROR,
                                            "Identifier required")

    object StatementRequired : ParserIssue(Level.ERROR,
                                           "Statement required")

    object ExpressionRequired : ParserIssue(Level.ERROR,
                                           "Expression required")

    object LiteralRequired : ParserIssue(Level.ERROR,
                                         "Literal required")

    object VariableKeywordRequired : ParserIssue(Level.ERROR,
                                                 "Variable keyword is required for defining variable")

    object VariableAssignmentOperatorRequired : ParserIssue(Level.ERROR,
                                                            "Variable must have assigned value")
}

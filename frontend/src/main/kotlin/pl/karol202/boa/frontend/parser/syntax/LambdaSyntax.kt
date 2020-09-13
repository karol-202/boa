package pl.karol202.boa.frontend.parser.syntax

import pl.karol202.boa.ast.LambdaNode
import pl.karol202.boa.ast.StatementSequenceNode
import pl.karol202.boa.frontend.lexer.Token

object LambdaSyntax : AbstractSyntax<LambdaNode>()
{
	override fun SyntaxScope.syntax() =
		token<Token.Parenthesis.Open>().then {
			syntax(ParametersListSyntax) then { parametersList ->
				token<Token.Parenthesis.Close>() then {
					token<Token.Special>() matching { it.value == "=>" } then {
						either<LambdaNode> {
							+ token<Token.Block.Open>().then {
								syntax(StatementSequenceSyntax) then { statementSequence ->
									token<Token.Block.Close>() then {
										LambdaNode(parametersList, statementSequence).finish()
									}
								}
							}
							+ syntax(StatementSyntax).then { statement ->
								LambdaNode(parametersList, StatementSequenceNode(statement)).finish()
							}
						}
					}
				}
			}
		}
}

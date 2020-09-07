package pl.karol202.boa.frontend.parser.syntax

import pl.karol202.boa.ast.IdentifierNode
import pl.karol202.boa.ast.VariableNode
import pl.karol202.boa.frontend.lexer.Token
import pl.karol202.boa.syntax.KeywordType
import pl.karol202.boa.syntax.VariableMutability

object VariableSyntax : AbstractSyntax<VariableNode>()
{
	override fun SyntaxScope.syntax() =
		token<Token.Keyword>() matching { it.type in KeywordType.variable } then { keyword ->
			syntax(IdentifierSyntax) then { identifier ->
				either<VariableNode> {
					+ token<Token.Special>().matching { it.value == ":" }.then {
						syntax(IdentifierSyntax) then { type ->
							valueAssignSyntax(keyword, identifier, type)
						}
					}
					+ valueAssignSyntax(keyword, identifier, null)
				}
			}
		}

	private fun SyntaxScope.valueAssignSyntax(keyword: Token.Keyword, identifier: IdentifierNode, type: IdentifierNode?) =
		token<Token.Special>() matching { it.value == "=" } then {
			syntax(ExpressionSyntax) then { value ->
				VariableNode(VariableMutability.findByKeyword(keyword.type), identifier, type, value).finish()
			}
		}
}

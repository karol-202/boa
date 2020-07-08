package pl.karol202.boa.frontend.parser.syntax

import pl.karol202.boa.ast.VariableNode
import pl.karol202.boa.frontend.lexer.Token
import pl.karol202.boa.syntax.KeywordType
import pl.karol202.boa.syntax.VariableType

object VariableSyntax : AbstractSyntax<VariableNode>()
{
	override fun SyntaxScope.parse() =
		token<Token.Keyword>() matching { it.type in KeywordType.variable } then { keyword ->
			syntax(IdentifierSyntax) then { identifier ->
				token<Token.Special>() matching { it.value == "=" } then {
					syntax(ExpressionSyntax) then { expression ->
						VariableNode(VariableType.findByKeyword(keyword.type), identifier, expression).finish()
					}
				}
			}
		}
}

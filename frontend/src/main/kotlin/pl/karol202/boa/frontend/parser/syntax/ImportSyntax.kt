package pl.karol202.boa.frontend.parser.syntax

import pl.karol202.boa.ast.ImportNode
import pl.karol202.boa.frontend.lexer.Token
import pl.karol202.boa.syntax.KeywordType

object ImportSyntax : AbstractSyntax<ImportNode>()
{
	override fun SyntaxScope.syntax() =
		token<Token.Keyword>() matching { it.type == KeywordType.IMPORT } then {
			syntax(StringLiteralSyntax) then { literal ->
				ImportNode(literal.value).finish()
			}
		}
}

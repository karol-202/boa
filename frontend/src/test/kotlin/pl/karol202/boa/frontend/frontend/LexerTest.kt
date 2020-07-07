package pl.karol202.boa.frontend.frontend

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import pl.karol202.boa.Phase
import pl.karol202.boa.frontend.lexer.Lexer
import pl.karol202.boa.frontend.lexer.Token
import pl.karol202.boa.syntax.KeywordType

class LexerTest : StringSpec()
{
	private val lexer = Lexer('\n')

	init
	{
		"Keyword" {
			val input = "var"
			lexer.process(input) shouldBe Phase.Result.Success(listOf(
				Token.Keyword(KeywordType.VAR)
			))
		}
		"Identifier" {
			val input = "test"
			lexer.process(input) shouldBe Phase.Result.Success(listOf(
				Token.Identifier("test")
			))
		}
		"String literal" {
			val input = "\"this is a literal\""
			lexer.process(input) shouldBe Phase.Result.Success(listOf(
				Token.StringLiteral("this is a literal")
			))
		}
		"Integer literal" {
			val input = "666"
			lexer.process(input) shouldBe Phase.Result.Success(listOf(
				Token.IntegerLiteral(666)
			))
		}
		"Real literal" {
			val input = "1.577"
			lexer.process(input) shouldBe Phase.Result.Success(listOf(
				Token.RealLiteral(1.577)
			))
		}
		"Variable declaration" {
			val input = "let test"
			lexer.process(input) shouldBe Phase.Result.Success(listOf(
				Token.Keyword(KeywordType.LET),
				Token.Identifier("test")
			))
		}
		"Variable assignment" {
			val input = "test = 6"
			lexer.process(input) shouldBe Phase.Result.Success(listOf(
				Token.Identifier("test"),
				Token.Special("="),
				Token.IntegerLiteral(6)
			))
		}
		"Variable assignment without spaces" {
			val input = "test=6"
			lexer.process(input) shouldBe Phase.Result.Success(listOf(
				Token.Identifier("test"),
				Token.Special("="),
				Token.IntegerLiteral(6)
			))
		}
		"Identifier and single-line comment" {
			val input = "test //comment"
			lexer.process(input) shouldBe Phase.Result.Success(listOf(
				Token.Identifier("test"),
				Token.SingleLineComment("comment")
			))
		}
		"Assignment with multi-line comment in single-line" {
			val input = "test /* comment */ += \"AAA\""
			lexer.process(input) shouldBe Phase.Result.Success(listOf(
				Token.Identifier("test"),
				Token.MultiLineComment(" comment "),
				Token.Special("+="),
				Token.StringLiteral("AAA")
			))
		}
		"Function invocation without parameters" {
			val input = "function()"
			lexer.process(input) shouldBe Phase.Result.Success(listOf(
				Token.Identifier("function"),
				Token.ParenthesisOpen,
				Token.ParenthesisClose
			))
		}
		"Function invocation with 2 parameters" {
			val input = "function(test, \"BBB\")"
			lexer.process(input) shouldBe Phase.Result.Success(listOf(
				Token.Identifier("function"),
				Token.ParenthesisOpen,
				Token.Identifier("test"),
				Token.Comma,
				Token.StringLiteral("BBB"),
				Token.ParenthesisClose
			))
		}
		"Multi-line comment" {
			val input = "/*te\nst*/"
			lexer.process(input) shouldBe Phase.Result.Success(listOf(
				Token.MultiLineComment("te\nst")
			))
		}
		"Multi-line string literal" {
			val input = "\"te\nst\""
			lexer.process(input) shouldBe Phase.Result.Success(listOf(
				Token.StringLiteral("te\nst")
			))
		}
		"String inside comment" {
			val input = "/*\"te\"st*/"
			lexer.process(input) shouldBe Phase.Result.Success(listOf(
				Token.MultiLineComment("\"te\"st")
			))
		}
		"Comment inside string" {
			val input = "\"this is /*literal*/\""
			lexer.process(input) shouldBe Phase.Result.Success(listOf(
				Token.StringLiteral("this is /*literal*/")
			))
		}
		"Function invocation in multiple lines" {
			val input = "print(3.2,\n\t\"test\")"
			lexer.process(input) shouldBe Phase.Result.Success(listOf(
				Token.Identifier("print"),
				Token.ParenthesisOpen,
				Token.RealLiteral(3.2),
				Token.Comma,
				Token.Newline,
				Token.StringLiteral("test"),
				Token.ParenthesisClose
			))
		}
	}
}

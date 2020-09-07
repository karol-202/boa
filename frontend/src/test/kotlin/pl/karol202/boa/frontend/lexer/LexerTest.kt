package pl.karol202.boa.frontend.lexer

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import pl.karol202.boa.Phase
import pl.karol202.boa.syntax.KeywordType

class LexerTest : StringSpec()
{
	private val lexer = Lexer

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
				Token.Literal.String("this is a literal")
			))
		}
		"Integer literal" {
			val input = "666"
			lexer.process(input) shouldBe Phase.Result.Success(listOf(
				Token.Literal.Integer(666)
			))
		}
		"Real literal" {
			val input = "1.577"
			lexer.process(input) shouldBe Phase.Result.Success(listOf(
				Token.Literal.Real(1.577)
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
				Token.Special("="), Token.Literal.Integer(6)
			))
		}
		"Variable assignment without spaces" {
			val input = "test=6"
			lexer.process(input) shouldBe Phase.Result.Success(listOf(
				Token.Identifier("test"),
				Token.Special("="), Token.Literal.Integer(6)
			))
		}
		"Identifier and single-line comment" {
			val input = "test //comment"
			lexer.process(input) shouldBe Phase.Result.Success(listOf(
				Token.Identifier("test"),
				Token.Comment.SingleLine("comment")
			))
		}
		"Assignment with multi-line comment in single-line" {
			val input = "test /* comment */ += \"AAA\""
			lexer.process(input) shouldBe Phase.Result.Success(listOf(
				Token.Identifier("test"),
				Token.Comment.MultiLine(" comment "),
				Token.Special("+="), Token.Literal.String("AAA")
			))
		}
		"Function invocation without parameters" {
			val input = "function()"
			lexer.process(input) shouldBe Phase.Result.Success(listOf(
				Token.Identifier("function"),
				Token.Parenthesis.Open,
				Token.Parenthesis.Close
			))
		}
		"Function invocation with 2 parameters" {
			val input = "function(test, \"BBB\")"
			lexer.process(input) shouldBe Phase.Result.Success(listOf(
				Token.Identifier("function"),
				Token.Parenthesis.Open,
				Token.Identifier("test"),
				Token.Comma, Token.Literal.String("BBB"),
				Token.Parenthesis.Close
			))
		}
		"Multi-line comment" {
			val input = "/*te\nst*/"
			lexer.process(input) shouldBe Phase.Result.Success(listOf(
				Token.Comment.MultiLine("te\nst")
			))
		}
		"Multi-line string literal" {
			val input = "\"te\nst\""
			lexer.process(input) shouldBe Phase.Result.Success(listOf(
				Token.Literal.String("te\nst")
			))
		}
		"String inside comment" {
			val input = "/*\"te\"st*/"
			lexer.process(input) shouldBe Phase.Result.Success(listOf(
				Token.Comment.MultiLine("\"te\"st")
			))
		}
		"Comment inside string" {
			val input = "\"this is /*literal*/\""
			lexer.process(input) shouldBe Phase.Result.Success(listOf(
				Token.Literal.String("this is /*literal*/")
			))
		}
		"Function invocation in multiple lines" {
			val input = "print(3.2,\n\t\"test\")"
			lexer.process(input) shouldBe Phase.Result.Success(listOf(
				Token.Identifier("print"),
				Token.Parenthesis.Open, Token.Literal.Real(3.2),
				Token.Comma,
				Token.Newline, Token.Literal.String("test"),
				Token.Parenthesis.Close
			))
		}
	}
}

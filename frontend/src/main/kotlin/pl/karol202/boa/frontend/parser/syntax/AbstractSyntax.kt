package pl.karol202.boa.frontend.parser.syntax

import pl.karol202.boa.ast.Node
import pl.karol202.boa.frontend.lexer.Token
import pl.karol202.boa.frontend.parser.ParseResult
import pl.karol202.boa.frontend.util.split

abstract class AbstractSyntax<N : Node> : Syntax<N>
{
	class SyntaxRule<out N : Node>(val parse: Scope.() -> ParseResult<N>)
	{
		class Scope(val tokens: List<Token>)
	}

	data class SyntaxScope(val tokens: List<Token>)
	{
		class SyntaxBuilder<N : Node>(private val syntax: Syntax<N>)
		{
			infix fun <R : Node> then(block: SyntaxScope.(N) -> SyntaxRule<R>) = SyntaxRule {
				syntax.parse(tokens).flatMap {
					SyntaxScope(remainingTokens).block(node).parse(this@SyntaxRule)
				}
			}

			fun just() = then { it.finish() }
		}

		@Suppress("UNCHECKED_CAST")
		class TokenBuilder<T : Token>(private val predicate: (Token) -> Boolean = { true })
		{
			infix fun matching(predicate: (T) -> Boolean) = TokenBuilder<T> { this.predicate(it) && predicate(it as T) }

			infix fun <R : Node> then(block: SyntaxScope.(T) -> SyntaxRule<R>) = SyntaxRule {
				val (token, remainingTokens) = tokens.split()
				if(!predicate(token)) ParseResult.Failure(remainingTokens)
				else SyntaxScope(remainingTokens).block(token as T).parse(this@SyntaxRule)
			}
		}

		class EitherBuilder<N : Node>
		{
			private val options = mutableListOf<SyntaxRule<N>>()

			operator fun SyntaxRule<N>.unaryPlus()
			{
				options += this
			}

			fun build() = SyntaxRule {
				options.asSequence().map { it.parse(this) }.firstOrNull { it is ParseResult.Success }
					?: ParseResult.Failure(tokens)
			}
		}

		fun <N : Node> syntax(syntax: Syntax<N>) = SyntaxBuilder(syntax)

		@JvmName("tokenTyped")
		inline fun <reified T : Token> token() = TokenBuilder<T> { it is T }

		fun token() = TokenBuilder<Token>()

		fun <N : Node> either(block: EitherBuilder<N>.() -> Unit) = EitherBuilder<N>().also(block).build()

		fun <N : Node> N.finish() = SyntaxRule {
			ParseResult.Success(this@finish, tokens)
		}

		fun fail() = SyntaxRule {
			ParseResult.Failure(tokens)
		}
	}

	override fun parse(tokens: List<Token>) = SyntaxScope(tokens).parse().parse(SyntaxRule.Scope(tokens))

	protected abstract fun SyntaxScope.parse(): SyntaxRule<N>
}

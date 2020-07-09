package pl.karol202.boa.frontend.parser.syntax

import pl.karol202.boa.ast.Node
import pl.karol202.boa.frontend.lexer.Token
import pl.karol202.boa.frontend.parser.ParseResult
import pl.karol202.boa.frontend.parser.TraceElement

abstract class AbstractSyntax<N : Node> : Syntax<N>
{
	data class SyntaxRule<out N : Node>(val parse: () -> ParseResult<N>)

	data class SyntaxScope(val tokens: List<Token>)
	{
		inner class SyntaxBuilder<N : Node>(private val syntax: Syntax<N>)
		{
			infix fun <R : Node> then(nextRule: SyntaxScope.(N) -> SyntaxRule<R>) = SyntaxRule {
				syntax.parse(tokens).fold(
					ifSuccess = {
						val nextResult = SyntaxScope(remainingTokens).nextRule(node).parse()
						nextResult.withTraceElement(TraceElement.SyntaxElement(syntax, traceElement, nextResult.traceElement))
					},
					ifFailure = {
						withTraceElement(TraceElement.SyntaxElement(syntax, traceElement))
					}
				)
			}

			fun just() = then { it.finish() }
		}

		@Suppress("UNCHECKED_CAST")
		inner class TokenBuilder<T : Token>(private val predicate: (Token) -> Boolean = { true })
		{
			infix fun matching(predicate: (T) -> Boolean) = TokenBuilder<T> { this.predicate(it) && predicate(it as T) }

			infix fun <R : Node> then(nextRule: SyntaxScope.(T) -> SyntaxRule<R>) = SyntaxRule {
				val token = tokens.firstOrNull()
					?: return@SyntaxRule ParseResult.Failure(emptyList(), TraceElement.TokenElement(null, false))
				val remainingTokens = tokens.drop(1)

				if(!predicate(token))
					return@SyntaxRule ParseResult.Failure(remainingTokens, TraceElement.TokenElement(null, false))

				val nextResult = SyntaxScope(remainingTokens).nextRule(token as T).parse()
				nextResult.withTraceElement(TraceElement.TokenElement(token, true, nextResult.traceElement))
			}
		}

		inner class EitherBuilder<N : Node>
		{
			private val options = mutableListOf<SyntaxRule<N>>()

			operator fun SyntaxRule<N>.unaryPlus()
			{
				options += this
			}

			fun build() = SyntaxRule {
				val processedElements = mutableListOf<TraceElement>()
				val eitherElement = TraceElement.EitherElement(processedElements)

				options.asSequence()
					.map { it.parse() }
					.onEach { processedElements += it.traceElement }
					.firstOrNull { it is ParseResult.Success }
					?.withTraceElement(eitherElement)
					?: ParseResult.Failure(tokens, eitherElement)
			}
		}

		fun <N : Node> syntax(syntax: Syntax<N>) = SyntaxBuilder(syntax)

		@JvmName("tokenTyped")
		inline fun <reified T : Token> token() = TokenBuilder<T> { it is T }

		fun token() = TokenBuilder<Token>()

		fun <N : Node> either(builder: EitherBuilder<N>.() -> Unit) = EitherBuilder<N>().also(builder).build()

		fun <N : Node> N.finish() = SyntaxRule {
			ParseResult.Success(this@finish, tokens, TraceElement.TerminalElement(true))
		}

		fun fail() = SyntaxRule {
			ParseResult.Failure(tokens, TraceElement.TerminalElement(false))
		}
	}

	override fun parse(tokens: List<Token>) = SyntaxScope(tokens).syntax().parse()

	protected abstract fun SyntaxScope.syntax(): SyntaxRule<N>
}

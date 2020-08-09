package pl.karol202.boa.frontend.transformer

import pl.karol202.boa.IssueProvider
import pl.karol202.boa.Phase
import pl.karol202.boa.syntax.Chars

object LineSeparatorTransformer : Phase<String, String>
{
	private const val CRLF = "\r\n"
	private const val LF = "\n"
	private const val CR = "\r"
	private const val REPLACEMENT = Chars.LINE_SEPARATOR.toString()

	data class Result(override val value: String) : Phase.Result.Success<String>,
	                                                IssueProvider by IssueProvider.noIssues

	override fun process(input: String) = input
		.replace(CRLF, REPLACEMENT)
		.replace(LF, REPLACEMENT)
		.replace(CR, REPLACEMENT)
		.let { Result(it) }
}

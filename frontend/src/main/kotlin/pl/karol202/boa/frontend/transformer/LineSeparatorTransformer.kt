package pl.karol202.boa.frontend.transformer

import pl.karol202.boa.IssueProvider
import pl.karol202.boa.Phase

class LineSeparatorTransformer(targetLineSeparator: Char) : Phase<String, String>
{
	companion object
	{
		private const val CRLF = "\r\n"
		private const val LF = "\n"
		private const val CR = "\r"
	}

	data class Result(override val value: String) : Phase.Result.Success<String>,
	                                                IssueProvider by IssueProvider.noIssues

	private val replacement = targetLineSeparator.toString()

	override fun process(input: String) = input
		.replace(CRLF, replacement)
		.replace(LF, replacement)
		.replace(CR, replacement)
		.let { Result(it) }
}

package pl.karol202.boa.frontend.transformer

import pl.karol202.boa.IssueProvider
import pl.karol202.boa.Phase
import pl.karol202.boa.frontend.lexer.Token

object CommentsRemover : Phase<List<Token>, List<Token>>
{
	data class Result(override val value: List<Token>) : Phase.Result.Success<List<Token>>,
	                                                     IssueProvider by IssueProvider.noIssues

	override fun process(input: List<Token>) = Result(input.filterNot { it is Token.Comment })
}

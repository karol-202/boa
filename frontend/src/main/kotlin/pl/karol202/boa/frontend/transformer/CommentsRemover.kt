package pl.karol202.boa.frontend.transformer

import pl.karol202.boa.IssueProvider
import pl.karol202.boa.Phase
import pl.karol202.boa.frontend.lexer.Token

object CommentsRemover : Phase<List<Token>, List<Token>>
{
	data class Result(override val result: List<Token>) : Phase.Result<List<Token>>,
	                                                      IssueProvider by IssueProvider.noIssues

	override fun process(input: List<Token>) = Result(input.filterNot { it is Token.Comment })
}

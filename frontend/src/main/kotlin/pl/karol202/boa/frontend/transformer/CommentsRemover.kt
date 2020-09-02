package pl.karol202.boa.frontend.transformer

import pl.karol202.boa.Phase
import pl.karol202.boa.frontend.lexer.Token

object CommentsRemover : Phase<List<Token>, List<Token>>
{
	override fun process(input: List<Token>) = Phase.Result.Success(input.filterNot { it is Token.Comment })
}

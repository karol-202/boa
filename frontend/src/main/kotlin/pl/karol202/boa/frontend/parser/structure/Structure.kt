package pl.karol202.boa.frontend.parser.structure

import pl.karol202.boa.ast.Node
import pl.karol202.boa.frontend.lexer.Token
import pl.karol202.boa.frontend.parser.ParseResult

interface Structure<N : Node>
{
	fun parse(tokens: List<Token>): ParseResult<N>
}

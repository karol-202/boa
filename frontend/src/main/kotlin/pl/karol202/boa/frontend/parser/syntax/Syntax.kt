package pl.karol202.boa.frontend.parser.syntax

import pl.karol202.boa.ast.Node
import pl.karol202.boa.frontend.lexer.Token
import pl.karol202.boa.frontend.parser.ParseResult

interface Syntax<N : Node>
{
	fun parse(tokens: List<Token>): ParseResult<N>
}

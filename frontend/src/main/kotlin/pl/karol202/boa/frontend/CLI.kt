package pl.karol202.boa.frontend

import pl.karol202.boa.frontend.lexer.Lexer
import pl.karol202.boa.frontend.parser.Parser

@OptIn(ExperimentalStdlibApi::class)
fun main()
{
	println("Boa Frontend CLI")
	println("------------------------------")
	println("Enter statement:")
	val input = System.`in`.readBytes().decodeToString()

	val lexer = Lexer('\n')
	val parser = Parser()

	val lexerResult = lexer.process(input)
	println("Lexer result: $lexerResult")

	val parserResult = parser.process(lexerResult.result)
	when(parserResult)
	{
		is Parser.Result.Success -> println("Parser result: ${parserResult.result}")
		is Parser.Result.Failure -> println("Parser failure")
	}
	println()
}

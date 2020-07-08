package pl.karol202.boa.frontend

import pl.karol202.boa.Phase
import pl.karol202.boa.frontend.lexer.Lexer
import pl.karol202.boa.frontend.parser.Parser

@OptIn(ExperimentalStdlibApi::class)
fun main()
{
	val input = System.`in`.readBytes().decodeToString()

	val lexer = Lexer('\n')
	val parser = Parser()

	when(val lexerResult = lexer.process(input))
	{
		is Phase.Result.Success ->
		{
			println("Lexer result: $lexerResult")
			when(val parserResult = parser.process(lexerResult.result))
			{
				is Phase.Result.Success -> println("Parser result: ${parserResult.result}")
				is Phase.Result.Failure -> println("Parser failure")
			}
		}
		is Phase.Result.Failure -> println("Lexer failure")
	}
}

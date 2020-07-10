package pl.karol202.boa.cli

import pl.karol202.boa.frontend.lexer.Lexer
import pl.karol202.boa.frontend.parser.Parser
import pl.karol202.boa.frontend.transformer.CommentsRemover
import pl.karol202.boa.frontend.transformer.LineSeparatorTransformer
import pl.karol202.boa.interpreter.Interpreter
import pl.karol202.boa.plus

private const val INTERMEDIATE_LINE_SEPARATOR = '\n'

private val pipeline =
	LineSeparatorTransformer(INTERMEDIATE_LINE_SEPARATOR) +
			Lexer(INTERMEDIATE_LINE_SEPARATOR) +
			CommentsRemover +
			Parser +
			Interpreter

fun main()
{
	println("Boa CLI")
	println("Press Ctrl+D to stop reading and execute")
	println("------------------------------")
	val input = System.`in`.readBytes().decodeToString()

	when(val result = pipeline.process(input))
	{
		is Interpreter.Result -> result.value.execute(System.`in`, System.out)
		else -> println(result)
	}
}

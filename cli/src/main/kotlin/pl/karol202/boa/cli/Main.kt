package pl.karol202.boa.cli

import com.github.ajalt.clikt.completion.CompletionCandidates
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.default
import com.github.ajalt.clikt.parameters.arguments.optional
import com.github.ajalt.clikt.parameters.types.file
import pl.karol202.boa.interpreter.Interpreter
import pl.karol202.boa.plus
import java.io.File

object CLI : CliktCommand()
{
	private val sourceFile: File by argument("FILE", help = "Boa source file",
	                                         completionCandidates = CompletionCandidates.Path).file(mustExist = true,
	                                                                                                canBeDir = false,
	                                                                                                mustBeReadable = true)

	private val pipeline = SourcesResolver + Interpreter

	override fun run()
	{
		val result = pipeline.process(sourceFile)
		result.fold(
			ifSuccess = { it.execute(System.`in`, System.out) },
			ifFailure = { println(result) }
		)
	}
}

fun main(args: Array<String>) = CLI.main(args)

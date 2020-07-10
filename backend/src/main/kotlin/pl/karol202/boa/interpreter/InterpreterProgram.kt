package pl.karol202.boa.interpreter

import pl.karol202.boa.ast.FileNode
import pl.karol202.boa.interpreter.context.InterpreterContext
import pl.karol202.boa.interpreter.context.Invocable
import pl.karol202.boa.interpreter.handler.FileHandler
import java.io.InputStream
import java.io.OutputStream

private val DEFAULT_VARIABLES = mapOf(
	"print" to Invocable.create { args ->
		output.writer().run {
			appendln(args[0].toString())
			flush()
		}
	}
)

class InterpreterProgram(private val fileNode: FileNode) : Program
{
	override fun execute(input: InputStream, output: OutputStream)
	{
		val context = InterpreterContext(input, output, DEFAULT_VARIABLES)
		context.handle(FileHandler, fileNode)
	}
}

package pl.karol202.boa.interpreter.program

import pl.karol202.boa.ast.FileWithImportsNode
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

class ProgramImpl(private val fileNodes: List<FileWithImportsNode>) : Program
{
	override fun execute(input: InputStream, output: OutputStream)
	{
		val initialContext = InterpreterContext(input, output, DEFAULT_VARIABLES)
		fileNodes.fold(initialContext) { context, node ->
			context.handle(FileHandler, node.file).context
		}
	}
}

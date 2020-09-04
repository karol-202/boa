package pl.karol202.boa.interpreter.data

interface Invocable
{
	fun invoke(context: InterpreterContext, arguments: List<Any>): Any
}

class BuiltinInvocable(private val function: InterpreterContext.(List<Any>) -> Any) : Invocable
{
	override fun invoke(context: InterpreterContext, arguments: List<Any>) = context.function(arguments)
}

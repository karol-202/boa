package pl.karol202.boa.interpreter.context

interface Invocable
{
	companion object
	{
		fun create(function: InterpreterContext.(List<Any>) -> Any) = object : Invocable
		{
			override fun invoke(context: InterpreterContext, arguments: List<Any>) = context.function(arguments)
		}
	}

	fun invoke(context: InterpreterContext, arguments: List<Any>): Any
}

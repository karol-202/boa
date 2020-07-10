package pl.karol202.boa

interface Phase<I, O>
{
	interface Result<out O> : IssueProvider
	{
		interface Success<out O> : Result<O>
		{
			override val value: O

			override fun <NO> flatMap(transform: (O) -> Result<NO>) = transform(value)
		}

		interface Failure : Result<Nothing>
		{
			override val value get() = null

			override fun <NO> flatMap(transform: (Nothing) -> Result<NO>) = this
		}

		val value: O?

		fun <NO> flatMap(transform: (O) -> Result<NO>): Result<NO>
	}

	fun process(input: I): Result<O>
}

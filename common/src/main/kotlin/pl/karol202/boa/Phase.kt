package pl.karol202.boa

interface Phase<I, O>
{
	sealed class Result<out O> : IssueProvider
	{
		data class Success<out O>(override val value: O,
		                          override val issues: List<Issue> = emptyList()) : Result<O>()
		{
			override fun <NO> flatMap(transform: (O) -> Result<NO>) = transform(value)
		}

		data class Failure(override val issues: List<Issue> = emptyList()) : Result<Nothing>()
		{
			override val value get() = null

			override fun <NO> flatMap(transform: (Nothing) -> Result<NO>) = this
		}

		abstract val value: O?

		abstract fun <NO> flatMap(transform: (O) -> Result<NO>): Result<NO>
	}

	fun process(input: I): Result<O>
}

package pl.karol202.boa

interface Phase<I, O>
{
	sealed class Result<out O> : IssueProvider
	{
		data class Success<out O>(override val value: O,
		                          override val issues: List<Issue> = emptyList()) : Result<O>()
		{
			override fun <NO> map(transform: (O) -> NO) = Success(transform(value), issues)

			override fun <NO> flatMap(transform: (O) -> Result<NO>) = transform(value)

			override fun <R> fold(ifSuccess: (O) -> R,
			                      ifFailure: () -> R) = ifSuccess(value)
		}

		data class Failure(override val issues: List<Issue> = emptyList()) : Result<Nothing>()
		{
			override val value get() = null

			override fun <NO> map(transform: (Nothing) -> NO) = Failure(issues)

			override fun <NO> flatMap(transform: (Nothing) -> Result<NO>) = this

			override fun <R> fold(ifSuccess: (Nothing) -> R,
			                      ifFailure: () -> R) = ifFailure()
		}

		abstract val value: O?

		abstract fun <NO> map(transform: (O) -> NO): Result<NO>

		abstract fun <NO> flatMap(transform: (O) -> Result<NO>): Result<NO>

		abstract fun <R> fold(ifSuccess: (O) -> R,
		                      ifFailure: () -> R): R
	}

	fun process(input: I): Result<O>
}

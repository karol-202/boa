package pl.karol202.boa

interface Phase<I, O>
{
	sealed class Result<O>
	{
		data class Success<O>(val result: O,
		                      val issues: List<Issue> = emptyList()) : Result<O>()

		data class Failure(val issues: List<Issue>): Result<Nothing>()
	}

	fun process(input: I): Result<O>
}

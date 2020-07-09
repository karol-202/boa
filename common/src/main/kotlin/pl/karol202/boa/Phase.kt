package pl.karol202.boa

interface Phase<I, O>
{
	interface Result<O> : IssueProvider
	{
		val result: O?
	}

	fun process(input: I): Result<O>
}

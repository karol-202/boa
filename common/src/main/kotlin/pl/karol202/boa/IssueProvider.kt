package pl.karol202.boa

interface IssueProvider
{
	companion object
	{
		val noIssues = object : IssueProvider {
			override val issues = emptyList<Issue>()
		}
	}

	val issues: List<Issue>
}

package pl.karol202.boa.syntax

enum class VariableMutability
{
	IMMUTABLE,
	MUTABLE;

	companion object
	{
		fun findByKeyword(keywordType: KeywordType) = when(keywordType)
		{
			KeywordType.LET -> IMMUTABLE
			KeywordType.VAR -> MUTABLE
			else -> throw IllegalStateException("Invalid variable keyword: $keywordType")
		}
	}
}

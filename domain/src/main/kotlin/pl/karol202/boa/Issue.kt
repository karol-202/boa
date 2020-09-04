package pl.karol202.boa

interface Issue
{
	enum class Level
	{
		WARNING,
		ERROR
	}

	val level: Level
	val message: String
}

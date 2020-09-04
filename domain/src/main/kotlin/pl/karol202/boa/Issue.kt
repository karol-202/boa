package pl.karol202.boa

open class Issue(private val level: Level,
                 private val message: String)
{
	enum class Level
	{
		WARNING,
		ERROR
	}

	override fun toString() = "${level.name} ${javaClass.name}: $message"
}

package pl.karol202.boa.type

interface Type
{
	val displayName: String

	fun isAssignableTo(other: Type): Boolean
}

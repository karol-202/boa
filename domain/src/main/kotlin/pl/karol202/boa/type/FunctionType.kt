package pl.karol202.boa.type

data class FunctionType(val argumentTypes: List<Type>,
                        val returnType: Type) : Type
{
	override val displayName get() = "(${argumentTypes.joinToString { it.displayName }} => ${returnType.displayName}"

	override fun isAssignableTo(other: Type) = other is AnyType || (other is FunctionType &&
			argumentTypes.size == other.argumentTypes.size &&
			argumentTypes.zip(other.argumentTypes).all { (myArg, otherArg) -> otherArg.isAssignableTo(myArg) } &&
			returnType.isAssignableTo(other.returnType))
}

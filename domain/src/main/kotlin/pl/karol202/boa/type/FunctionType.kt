package pl.karol202.boa.type

data class FunctionType(val parameterTypes: List<Type>,
                        val returnType: Type) : Type
{
	override val displayName get() = "(${parameterTypes.joinToString { it.displayName }} => ${returnType.displayName}"

	override fun isAssignableTo(other: Type) = other is AnyType || (other is FunctionType &&
			parameterTypes.size == other.parameterTypes.size &&
			parameterTypes.zip(other.parameterTypes).all { (myArg, otherArg) -> otherArg.isAssignableTo(myArg) } &&
			returnType.isAssignableTo(other.returnType))
}

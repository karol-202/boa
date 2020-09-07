package pl.karol202.boa.type

abstract class PrimitiveType(override val displayName: String) : Type
{
	override fun isAssignableTo(other: Type) = other == AnyType || other == this
}

object AnyType : PrimitiveType("Any")

object VoidType : PrimitiveType("Void")

object BoolType : PrimitiveType("Bool")

object IntType : PrimitiveType("Int")

object RealType : PrimitiveType("Real")

object StringType : PrimitiveType("String")

object TypeType : PrimitiveType("Type")

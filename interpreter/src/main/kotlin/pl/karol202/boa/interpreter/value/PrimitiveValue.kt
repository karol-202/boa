package pl.karol202.boa.interpreter.value

import pl.karol202.boa.type.*

abstract class PrimitiveValue(override val type: Type) : Value

object VoidValue : PrimitiveValue(VoidType)

class BoolValue(val value: Boolean) : PrimitiveValue(BoolType)

class IntValue(val value: Int) : PrimitiveValue(IntType)

class RealValue(val value: Double) : PrimitiveValue(RealType)

class StringValue(val value: String) : PrimitiveValue(StringType)

class TypeValue(val value: Type) : PrimitiveValue(TypeType)

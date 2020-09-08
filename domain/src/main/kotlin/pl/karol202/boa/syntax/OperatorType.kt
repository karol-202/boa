package pl.karol202.boa.syntax

enum class OperatorType(val symbol: String,
                        val position: Position)
{
	UNARY_PLUS("+", Position.UNARY_BEFORE),
	UNARY_MINUS("-", Position.UNARY_BEFORE),

	PLUS("+", Position.BINARY),
	MINUS("-", Position.BINARY),

	EQUAL("===", Position.BINARY);

	enum class Position
	{
		BINARY,
		UNARY_BEFORE
	}

	companion object
	{
		fun find(symbol: String, position: Position) =
			values().firstOrNull { it.symbol == symbol && it.position == position }
	}
}

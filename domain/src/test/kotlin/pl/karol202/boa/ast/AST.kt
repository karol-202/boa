package pl.karol202.boa.ast

/*
val line = readLine()
var number = parseInt(line)
number = number + 1
println(number)
 */
val testAST = FileNode(statements = listOf(
		VariableNode(type = VariableType.IMMUTABLE,
		             identifier = IdentifierNode("line"),
		             value = InvocationNode(target = IdentifierNode("readLine"),
		                                    parameters = emptyList())),
		VariableNode(type = VariableType.MUTABLE,
		             identifier = IdentifierNode("number"),
		             value = InvocationNode(target = IdentifierNode("parseInt"),
		                                    parameters = listOf(
				                                    IdentifierNode("line")
		                                    ))),
		AssignmentNode(target = IdentifierNode("number"),
		               value = InvocationNode(target = OperatorNode(Operator.PLUS),
		                                      parameters = listOf(
				                                      IdentifierNode("number"),
				                                      LiteralNode.IntegerLiteral(1)
		                                      ))),
		InvocationNode(target = IdentifierNode("println"),
		               parameters = listOf(
				               IdentifierNode("number")
		               ))
))

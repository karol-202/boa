package pl.karol202.boa.ast

import pl.karol202.boa.syntax.OperatorType
import pl.karol202.boa.syntax.VariableType

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
	                                    arguments = ArgumentsListNode()
	             )),
	VariableNode(type = VariableType.MUTABLE,
		             identifier = IdentifierNode("number"),
		             value = InvocationNode(target = IdentifierNode("parseInt"),
		                                    arguments = ArgumentsListNode(
			                                    IdentifierNode("line")
		                                    ))),
	AssignmentNode(target = IdentifierNode("number"),
		               value = InvocationNode(target = OperatorNode(OperatorType.PLUS),
		                                      arguments = ArgumentsListNode(
			                                      IdentifierNode("number"),
			                                      LiteralNode.IntegerLiteral(1)
		                                      ))),
	InvocationNode(target = IdentifierNode("println"),
	               arguments = ArgumentsListNode(
		               IdentifierNode("number")
	               ))
))

package pl.karol202.boa.interpreter.handler

import pl.karol202.boa.ast.FileNode
import pl.karol202.boa.interpreter.data.InterpreterContext

object FileHandler : Handler<FileNode, Unit>
{
	override fun InterpreterContext.handle(node: FileNode) = handle(StatementSequenceHandler, node.statements)
}

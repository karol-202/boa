package pl.karol202.boa.ast

data class FileWithImportsNode(val file: FileNode,
                               val imports: List<FileNode>) : Node

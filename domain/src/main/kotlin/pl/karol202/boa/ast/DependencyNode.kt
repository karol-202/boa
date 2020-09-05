package pl.karol202.boa.ast

data class DependencyNode(val fileNode: FileNode,
                          val dependencies: List<DependencyNode>)

package pl.karol202.boa.cli

import pl.karol202.boa.Issue
import pl.karol202.boa.Phase
import java.io.File

object FileReader : Phase<File, String>
{
	class FileReadIssue(path: String) : Issue(Level.ERROR, "Cannot read file: $path")

	override fun process(input: File) =
		runCatching { input.readText() }.fold(onSuccess = { Phase.Result.Success(it) },
		                                      onFailure = { Phase.Result.Failure(FileReadIssue(input.absolutePath)) })
}

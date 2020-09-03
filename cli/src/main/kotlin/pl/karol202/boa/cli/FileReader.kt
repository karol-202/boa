package pl.karol202.boa.cli

import pl.karol202.boa.Phase
import java.io.File

object FileReader : Phase<File, String>
{
	override fun process(input: File) =
		runCatching { input.readText() }.fold(onSuccess = { Phase.Result.Success(it) },
		                                      onFailure = { Phase.Result.Failure() })
}

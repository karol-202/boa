package pl.karol202.boa.cli

import pl.karol202.boa.Phase
import pl.karol202.boa.frontend.lexer.Lexer
import pl.karol202.boa.frontend.parser.Parser
import pl.karol202.boa.frontend.transformer.CommentsRemover
import pl.karol202.boa.frontend.transformer.ImportsExtractor
import pl.karol202.boa.frontend.transformer.LineSeparatorTransformer
import pl.karol202.boa.plus
import java.io.File

object FilePipeline : Phase<File, ImportsExtractor.FileNodeAndImports>
{
	private val pipeline =
		FileReader +
				LineSeparatorTransformer +
				Lexer +
				CommentsRemover +
				Parser +
				ImportsExtractor

	override fun process(input: File) = pipeline.process(input)
}

package pl.karol202.boa.frontend.transformer

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.beInstanceOf
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import pl.karol202.boa.Phase
import pl.karol202.boa.frontend.parser.Parser
import pl.karol202.boa.frontend.transformer.LineSeparatorTransformer

class LineSeparatorTransformerTest : StringSpec()
{
	init
	{
		"String without separators should not be transformed" {
			val transformer = LineSeparatorTransformer('\n')
			val input = "test test"

			val output = transformer.process(input)
			output should beInstanceOf<Parser.Result.Success>()
			output.result shouldBe input
		}
		"String with LF should not be transformed" {
			val transformer = LineSeparatorTransformer('\n')
			val input = "test \ntest"

			val output = transformer.process(input)
			output should beInstanceOf<Parser.Result.Success>()
			output.result shouldBe input
		}
		"String with CR should be transformed to LF" {
			val transformer = LineSeparatorTransformer('\n')
			val input = "test \rtest"

			val output = transformer.process(input)
			output should beInstanceOf<Parser.Result.Success>()
			output.result shouldBe "test \ntest"
		}
		"String with mixed separators should be transformed to LF" {
			val transformer = LineSeparatorTransformer('\n')
			val input = "test \rtest\n\r\n test\ntest"

			val output = transformer.process(input)
			output should beInstanceOf<Parser.Result.Success>()
			output.result shouldBe "test \ntest\n\n test\ntest"
		}
	}
}

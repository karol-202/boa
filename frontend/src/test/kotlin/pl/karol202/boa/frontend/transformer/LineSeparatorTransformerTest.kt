package pl.karol202.boa.frontend.transformer

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.beInstanceOf
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import pl.karol202.boa.Phase
import pl.karol202.boa.frontend.parser.Parser

class LineSeparatorTransformerTest : StringSpec()
{
	init
	{
		"String without separators should not be transformed" {
			val transformer = LineSeparatorTransformer
			val input = "test test"

			val output = transformer.process(input)
			output should beInstanceOf<Phase.Result.Success<String>>()
			output.value shouldBe input
		}
		"String with LF should not be transformed" {
			val transformer = LineSeparatorTransformer
			val input = "test \ntest"

			val output = transformer.process(input)
			output should beInstanceOf<Phase.Result.Success<String>>()
			output.value shouldBe input
		}
		"String with CR should be transformed to LF" {
			val transformer = LineSeparatorTransformer
			val input = "test \rtest"

			val output = transformer.process(input)
			output should beInstanceOf<Phase.Result.Success<String>>()
			output.value shouldBe "test \ntest"
		}
		"String with mixed separators should be transformed to LF" {
			val transformer = LineSeparatorTransformer
			val input = "test \rtest\n\r\n test\ntest"

			val output = transformer.process(input)
			output should beInstanceOf<Phase.Result.Success<String>>()
			output.value shouldBe "test \ntest\n\n test\ntest"
		}
	}
}

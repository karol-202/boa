package pl.karol202.boa.frontend.transformer

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import pl.karol202.boa.Phase
import pl.karol202.boa.frontend.transformer.LineSeparatorTransformer

class LineSeparatorTransformerTest : StringSpec()
{
	init
	{
		"String without separators should not be transformed" {
			val transformer = LineSeparatorTransformer('\n')
			val input = "test test"
			val expected = Phase.Result.Success(input)
			transformer.process(input) shouldBe expected
		}
		"String with LF should not be transformed" {
			val transformer = LineSeparatorTransformer('\n')
			val input = "test \ntest"
			val expected = Phase.Result.Success(input)
			transformer.process(input) shouldBe expected
		}
		"String with CR should be transformed to LF" {
			val transformer = LineSeparatorTransformer('\n')
			val input = "test \rtest"
			val expected = Phase.Result.Success("test \ntest")
			transformer.process(input) shouldBe expected
		}
		"String with mixed separators should be transformed to LF" {
			val transformer = LineSeparatorTransformer('\n')
			val input = "test \rtest\n\r\n test\ntest"
			val expected = Phase.Result.Success("test \ntest\n\n test\ntest")
			transformer.process(input) shouldBe expected
		}
	}
}

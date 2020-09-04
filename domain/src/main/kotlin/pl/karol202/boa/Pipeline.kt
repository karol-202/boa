package pl.karol202.boa

private class PipelinePhase<I, O>(private val function: (I) -> Phase.Result<O>) : Phase<I, O>
{
	override fun process(input: I) = function(input)
}

operator fun <I, O, NO> Phase<I, O>.plus(next: Phase<O, NO>): Phase<I, NO> = PipelinePhase { input ->
	this.process(input).flatMap { intermediate ->
		next.process(intermediate)
	}
}

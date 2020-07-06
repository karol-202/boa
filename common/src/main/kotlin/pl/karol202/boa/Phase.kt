package pl.karol202.boa

interface Phase<I, O>
{
	fun process(input: I): O
}

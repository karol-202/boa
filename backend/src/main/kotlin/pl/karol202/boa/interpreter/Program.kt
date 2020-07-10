package pl.karol202.boa.interpreter

import java.io.InputStream
import java.io.OutputStream

interface Program
{
	fun execute(input: InputStream, output: OutputStream)
}

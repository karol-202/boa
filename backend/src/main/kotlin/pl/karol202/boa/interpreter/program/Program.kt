package pl.karol202.boa.interpreter.program

import java.io.InputStream
import java.io.OutputStream

interface Program
{
	fun execute(input: InputStream, output: OutputStream)
}

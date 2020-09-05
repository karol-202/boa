package pl.karol202.boa.interpreter.util

fun <K, V> Map<K, V>.updated(keyToChange: K, updater: (V) -> V) =
	mapValues { (key, value) -> if(key == keyToChange) updater(value) else value }

package day10.part2

import day10.part1.knotHash
import java.io.BufferedReader
import java.util.*

/**
 * @author verwoerd
 * @since 22/11/2021
 */
fun part2(input: BufferedReader): Any {
  return fullKnotHash(input.readLine())
}

fun fullKnotHash(input: String): String {
  val skipList = input.trim().map { it.code } + listOf(17, 31, 73, 47, 23)
  val list = (0 until 256).toCollection(LinkedList())
  val sparse = knotHash(list, skipList, 64)
  val dense = sparse.windowed(16, step = 16).map {
    it.reduce { a, b -> a xor b }
  }.joinToString(separator = "") { Integer.toHexString(it).padStart(2, '0') }
  return dense
}

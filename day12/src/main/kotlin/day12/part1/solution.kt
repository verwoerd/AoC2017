package day12.part1

import java.io.BufferedReader
import java.util.*
import kotlin.math.min

/**
 * @author verwoerd
 * @since 23/11/2021
 */
fun part1(input: BufferedReader): Any {
  val connections = input.lineSequence().map { parseLine(it) }.toMap()
  val seen = mutableSetOf(0)
  val queue = LinkedList<Int>().also { it.add(0) }
  while(queue.isNotEmpty()) {
    val current = queue.removeFirst()
    connections[current]!!.filter { seen.add(it)}.toCollection(queue)
  }
  return seen.size
}




val regex = Regex("(\\d+) <-> ([\\d ,]+)")
fun parseLine(line: String): Pair<Int, List<Int>> {
  val (from, to) = regex.matchEntire(line)?.destructured ?: error("Can't parse line $line")
  return from.toInt() to to.split(", ").map { it.toInt() }
}

package day12.part2

import day12.part1.parseLine
import java.io.BufferedReader
import java.util.*

/**
 * @author verwoerd
 * @since 23/11/2021
 */
fun part2(input: BufferedReader): Any {
  val connections = input.lineSequence().map { parseLine(it) }.toMap()
  val seen = mutableSetOf<Int>()
  val queue = LinkedList<Int>()
  var groups = 0
  while (seen.size < connections.size) {
    groups++
    val nextStart = connections.keys.first { it !in seen }
    seen.add(nextStart)
    queue.add(nextStart)
    while (queue.isNotEmpty()) {
      val current = queue.removeFirst()
      connections[current]!!.filter { seen.add(it) }.toCollection(queue)
    }
  }
  return groups
}

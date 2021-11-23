package day14.part2

import Coordinate
import adjacentCoordinates
import day10.part2.fullKnotHash
import java.io.BufferedReader
import java.util.*

/**
 * @author verwoerd
 * @since 23/11/2021
 */
fun part2(input: BufferedReader): Any {
  val key = input.readLine()
  val bits = mutableSetOf<Coordinate>()
  (0..127).forEach { row ->
    val hash = fullKnotHash("$key-$row")
    hash.mapIndexed { index, char ->
      val value = char.digitToInt(16)
      if (value and 8 != 0) bits.add(Coordinate(index * 4, row))
      if (value and 4 != 0) bits.add(Coordinate(index * 4 + 1, row))
      if (value and 2 != 0) bits.add(Coordinate(index * 4 + 2, row))
      if (value and 1 != 0) bits.add(Coordinate(index * 4 + 3, row))
    }
  }
  val seen = mutableSetOf<Coordinate>()
  val queue = LinkedList<Coordinate>()
  var regions = 0
  while (seen.size < bits.size) {
    val next = bits.first { it !in seen }
    queue.add(next)
    seen.add(next)
    regions++
    while (queue.isNotEmpty()) {
      val current = queue.pop()
      adjacentCoordinates(current)
        .filter { it in bits }
        .filter { seen.add(it) }
        .toCollection(queue)
    }
  }
  return regions
}

package day05.part1

import io.kotest.matchers.doubles.bePositiveInfinity
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 19/11/2021
 */
fun part1(input: BufferedReader): Any {
  val maze = input.lineSequence().map { it.toInt() }.toMutableList()
  var position = 0
  var steps = 0
  while (position in maze.indices) {
    val offset = maze[position]
    maze[position] = offset + 1
    position += offset
    steps++
  }
  return steps
}

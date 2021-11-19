package day05.part2

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 19/11/2021
 */
fun part2(input: BufferedReader): Any {
  val maze = input.lineSequence().map { it.toInt() }.toMutableList()
  var position = 0
  var steps = 0
  while (position in maze.indices) {
    val offset = maze[position]
    maze[position] = offset + when (offset) {
      in 3..Int.MAX_VALUE -> -1
      else -> 1
    }
    position += offset
    steps++
  }
  return steps
}

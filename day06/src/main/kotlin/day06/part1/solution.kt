package day06.part1

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 19/11/2021
 */
fun part1(input: BufferedReader): Any {
  val initialMemory = input.readLine().split(Regex("\\s+")).map { it.toLong() }.toList()
  val seen = mutableSetOf(initialMemory)
  var current = initialMemory
  var steps = 0
  do {
    val max = current.maxOrNull()!!
    val index = current.indexOfFirst { it == max }
    val increment = max / current.size
    val remainder = max % current.size
    current = current.mapIndexed { currentIndex, it ->
      when (currentIndex) {
        index -> 0
        else -> it
      } + increment + when {
        currentIndex in index + 1..index + remainder -> 1
        currentIndex + current.size in index + 1..index + remainder -> 1
        else -> 0
      }
    }.toMutableList()
    steps++
  } while (seen.add(current))

  return steps
}

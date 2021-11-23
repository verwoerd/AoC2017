package day13.part1

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 23/11/2021
 */
fun part1(input: BufferedReader): Any {
  val firewalls = input.lineSequence()
    .map { line -> line.split(": ").map { it.toInt() }.take(2).let { (a, b) -> a to b } }
    .toMap()
  return firewalls
    .filter { (time, depth) ->
      time == 0 || time % (depth*2-2) == 0
    }.map { (time, depth) -> time * depth }.sum()
}

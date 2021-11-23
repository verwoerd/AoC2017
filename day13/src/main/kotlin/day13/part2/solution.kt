package day13.part2

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 23/11/2021
 */
fun part2(input: BufferedReader): Any {
  val firewalls = input.lineSequence()
    .map { line -> line.split(": ").map { it.toInt() }.take(2).let { (a, b) -> a to b } }
    .toMap()
  return generateSequence(0) { it + 1 }
    .first { time -> firewalls.none { (diff, depth) -> time == 0 || (time+diff) % (depth * 2 - 2) == 0 } }
}

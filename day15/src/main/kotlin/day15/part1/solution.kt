package day15.part1

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 24/11/2021
 */
fun part1(input: BufferedReader): Any {
  val a = REGEX.matchEntire(input.readLine())!!.destructured.component1().toLong()
  val b = REGEX.matchEntire(input.readLine())!!.destructured.component1().toLong()
  return findMatches(a, b, 40_000_000)
}


fun findMatches(a: Long, b: Long, times: Int) =
  generateSequence(a to b) { (a, b) ->
    (a * 16807L) % 2147483647L to (b * 48271L) % 2147483647
  }.take(times)
    .count { (a, b) -> (a and 0xFFFF) == (b and 0xFFFF) }


val REGEX = Regex("Generator \\w starts with (\\d+)")

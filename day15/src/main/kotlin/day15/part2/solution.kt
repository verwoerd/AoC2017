package day15.part2

import day15.part1.REGEX
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 24/11/2021
 */
fun part2(input: BufferedReader): Any {
  val a = REGEX.matchEntire(input.readLine())!!.destructured.component1().toLong()
  val b = REGEX.matchEntire(input.readLine())!!.destructured.component1().toLong()
  return findMatches(a, b, 5_000_000)
}

fun findMatches(a: Long, b: Long, times: Int): Int {
  val aSequence = generateSequence(a) {
    var current = it
    do {
      current = (current * 16807L) % 2147483647L
    } while (current % 4 != 0L)
    current
  }
  return generateSequence(b) {
    var current = it
    do {
      current = (current * 48271L) % 2147483647
    } while (current % 8 != 0L)
    current
  }.zip(aSequence)
    .take(times)
    .count { (a, b) -> (a and 0xFFFF) == (b and 0xFFFF) }
}

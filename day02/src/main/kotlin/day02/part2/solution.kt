package day02.part2

import combinations
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 18/11/2021
 */
fun part2(input: BufferedReader): Any {
  return input.lineSequence()
    .map { line -> line.split(Regex("\\s+")).map { it.toLong() } }
    .map { combinations(2, it).first { (a,b) -> a % b == 0L || b % a == 0L } }
    .map { (a,b) -> when {
      a > b -> a/b
      else -> b/a
    } }
    .sum()
}

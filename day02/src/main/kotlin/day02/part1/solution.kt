package day02.part1

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 18/11/2021
 */
fun part1(input: BufferedReader): Any {
  return input.lineSequence()
    .map { line -> line.split(Regex("\\s+")).map { it.toLong() } }
    .map { it.maxOrNull()!! - it.minOrNull()!! }
    .sum()
}

package day11.part2

import day11.part1.distanceFromOrigin
import day11.part1.moveHex
import origin
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 23/11/2021
 */
fun part2(input: BufferedReader): Any {
  val steps = input.readLine().split(",")
  return steps.runningFold(origin) { acc, s -> acc.moveHex(s) }.maxOf { it.distanceFromOrigin() }
}

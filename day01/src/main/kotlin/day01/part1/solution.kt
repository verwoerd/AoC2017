package day01.part1

import toIntValue
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 18/11/2021
 */
fun part1(input: BufferedReader): Any {
  val line = input.readLine()
  return line.windowed(2, partialWindows = true) {
    when (it.length) {
      2 -> when (it[0]) {
        it[1] -> it[0].toIntValue()
        else -> 0
      }
      1 -> when (it[0]) {
        line[0] -> it[0].toIntValue()
        else -> 0
      }
      else -> error("invalid window")
    }
  }.sum()
}

package day01.part2

import toIntValue
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 18/11/2021
 */
fun part2(input: BufferedReader): Any {
  val line = input.readLine()
  val halfway =  line.length/2
  return line.windowed(line.length/2+1) {
    when(it[0]) {
      it[halfway] -> it[0].toIntValue()
      else -> 0
    }
  }.sum() * 2
}

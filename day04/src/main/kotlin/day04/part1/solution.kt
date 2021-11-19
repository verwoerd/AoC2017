package day04.part1

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 19/11/2021
 */
fun part1(input: BufferedReader): Any {
  return input.lineSequence()
    .count { line ->
      line.split(" ").let {
        it.size == it.distinct().size
      }
    }
}

package day14.part1

import day10.part2.fullKnotHash
import java.io.BufferedReader


/**
 * @author verwoerd
 * @since 23/11/2021
 */
fun part1(input: BufferedReader): Any {
  val key = input.readLine()
  return (0..127).sumOf { row ->
    val hash = fullKnotHash("$key-$row")
    hash.sumOf {
      when (it) {
        '0' -> 0L
        '1', '2', '4', '8' -> 1
        '3', '5', '6', '9', 'a', 'c' -> 2
        '7', 'b', 'd', 'e' -> 3
        'f' -> 4
        else -> error("invalid digit: it")
      }
    }
  }
}

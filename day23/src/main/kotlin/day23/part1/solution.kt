package day23.part1

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 26/11/2021
 */
fun part1(input: BufferedReader): Any {
  val b = input.readLine().split(" ").last().toInt()
  val eloop = b - 2
  val dloop = b - 2
  return eloop * dloop
}


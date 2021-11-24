package day17.part2

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 24/11/2021
 */
fun part2(input: BufferedReader): Any {
  val steps = input.readLine().toInt()
  return generateSequence(1 to 1) { (a, b) ->
    a + 1 to (b + steps) % (a + 1) + 1
  }.take(50_000_000)
    .filter { (_, b) -> b == 1 }
    .last().first
}

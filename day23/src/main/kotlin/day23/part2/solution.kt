package day23.part2

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 26/11/2021
 */
fun part2(input: BufferedReader): Any {
  val b = input.readLine().split(" ").last().toLong() * 100 + 100000
  return (b..b + 17_000 step 17).count { !it.toBigInteger().isProbablePrime(17) }
}

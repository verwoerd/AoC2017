package day03.part2

import Coordinate
import adjacentCircularCoordinates
import origin
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 18/11/2021
 */
fun part2(input: BufferedReader): Any {
  val map = mutableMapOf(origin to 1L).withDefault { 0 }
  val target = input.readLine().toLong()
  val coordinate = generateSequence(3) { it + 2 }
    .map { spiral ->
      val middle = spiral / 2
      val coordinates =
        (-middle + 1..middle).map { Coordinate(middle, it) } +
            (middle - 1 downTo -middle).map { Coordinate(it, middle) } +
            (middle - 1 downTo -middle).map { Coordinate(-middle, it) } +
            (-middle + 1..middle).map { Coordinate(it, -middle) }
      coordinates.firstOrNull { coordinate ->
        val value = adjacentCircularCoordinates(coordinate).map { map.getValue(it) }.sum()
        map[coordinate] = value
        value > target
      }
    }.filterNotNull().first()
  return map[coordinate]!!
}

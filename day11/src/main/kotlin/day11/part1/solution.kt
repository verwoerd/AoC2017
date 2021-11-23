package day11.part1

import Coordinate
import origin
import java.io.BufferedReader
import kotlin.math.abs

/**
 * @author verwoerd
 * @since 23/11/2021
 */
fun part1(input: BufferedReader): Any {
  val steps = input.readLine().split(",")
  val location = calculateLocation(steps)
  return location.distanceFromOrigin()
}

fun calculateLocation(steps: List<String>) = steps.fold(origin) { acc, s -> acc.moveHex(s) }

fun Coordinate.moveHex(direction: String) = when (direction) {
  "n" -> this + Coordinate(0, 2)
  "ne" -> this + Coordinate(1, 1)
  "se" -> this + Coordinate(1, -1)
  "s" -> this + Coordinate(0, -2)
  "sw" -> this + Coordinate(-1, -1)
  "nw" -> this + Coordinate(-1, 1)
  else -> error("invalid selection")
}

fun Coordinate.distanceFromOrigin() =
  abs(x) + (abs(y) -abs(x)) /2
